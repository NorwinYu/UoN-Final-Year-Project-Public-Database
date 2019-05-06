package wikipedia.parser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import name.fraser.neil.plaintext.StandardBreakScorer;
import name.fraser.neil.plaintext.diff_match_patch;
import name.fraser.neil.plaintext.diff_match_patch.Diff;
import wikipedia.model.WikipediaPage;
import wikipedia.model.WikipediaRevision;
import wikipedia.parser.XMLActor.AddRevisions;



/**
 * 
 * The PageManager collects multiple revisions by the same author until another author submits a revision (aggregation by author).
 * Only the last revision is kept and the difference to the previous revision (by another author) is calculated.
 * 
 * Usually, the revision are ordered chronologically in the Wikipedia-dump. But sometimes, the revisions are out of order.
 * In this case, the list of revisions is forked into two tails and reunited as soon as possible. 
 * 
 * The parentId of the entries in the xml-file is not reliable and overwritten.
 * 
 * @author zschache
 *
 */
public class PageManager {
	
//	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
	
	private final LoggingAdapter log;
	
	private LinkedList<WikipediaRevision> revisions = new LinkedList<WikipediaRevision>();
	private LinkedList<WikipediaRevision> forkedRevisions = new LinkedList<WikipediaRevision>();
	private LocalDateTime forkedDateTime;
			
	private ActorRef neo4jActor;
	private ActorRef mongoActor;
	
	private String currentUser;	
	private WikipediaRevision currentUserRevision;
	
	private long noChars;
	private long noCharsDiffs;
	
	private diff_match_patch dmp = new diff_match_patch(new StandardBreakScorer() );
	
	public PageManager(ActorRef neo4jActor, ActorRef mongoActor, ActorContext context) {
		this.neo4jActor = neo4jActor;
		this.mongoActor = mongoActor;
		log = Logging.getLogger(context.system(), this);
		
	}
	public void addRevision(WikipediaRevision revision) {

		// check for new user
		if (currentUser == null || 
				(revision.getContributorIp() == null && revision.getContributor() == null) ||
				(revision.getContributorIp() == null && !currentUser.equals(revision.getContributor().getId())) ||
			    (revision.getContributor() == null && !currentUser.equals(revision.getContributorIp()))) {
			
			addCurrentUserRevision();
			
			// change currentUser
			if (revision.getContributor() != null)
				currentUser = revision.getContributor().getId();
			else 
				currentUser = revision.getContributorIp();
			
			currentUserRevision = revision; 
										
		} else if (currentUserRevision.getTimestampDate().isBefore(revision.getTimestampDate())) {
			// look for newest Revision of currentUser
			currentUserRevision = revision; 
		}
		
	}
	
	public void addPage(WikipediaPage page) {
		
		addCurrentUserRevision();
		
		if (forkedDateTime != null && forkedDateTime.isBefore(currentUserRevision.getTimestampDate())) {
			appendForkedRevisions();
		}
		
		log.info("adding page " + page.getId() + " (" + page.getTitle() + ") with " + revisions.size() + 
				" revisions; Required memory (Full): " + Math.round(8 * (int)(((noChars * 2) + 45) / 8) / 1024 / 1024) + 
				" MB ; Required memory (Diffs): " + Math.round(8 * (int)(((noCharsDiffs * 2) + 45) / 8) / 1024 / 1024) + " MB");
		
		if (!revisions.isEmpty()) {
			
			// check integrity
			String text = revisions.getFirst().getText();
			for (WikipediaRevision r : revisions) {
				if (r.getPatch() != null) {
					text = applyPatch(text, r.getPatch());
				}
			}
			if (!revisions.getLast().getText().equals(text)) {
				log.warning("patches do not add up to the final version of page " + revisions.getLast().getPage().getId() + " : " + text);
			}
			
			revisions.getLast().setText(null);
			neo4jActor.tell(new AddRevisions(page, revisions), ActorRef.noSender());			
			mongoActor.tell(new AddRevisions(page, revisions), ActorRef.noSender());
		}
	}
	
	private void addCurrentUserRevision() {
		
		if (currentUserRevision != null) { // addRevision was called at least once
						
			if (forkedDateTime != null && forkedDateTime.isBefore(currentUserRevision.getTimestampDate())) {
				appendForkedRevisions();
			}
						
			// check whether current revision was written before the last revision
			if (!revisions.isEmpty() && currentUserRevision.getTimestampDate().isBefore(revisions.getLast().getTimestampDate())) {
				
				// if there is a pending fork, resolve it first
				if (forkedDateTime != null) {
					appendForkedRevisions();
				}
				
				// find last revision that is before current revision
				Iterator<WikipediaRevision> revRev = revisions.descendingIterator();
				WikipediaRevision lastRevision = revRev.next();
				int index = revisions.size() - 1;
				while(revRev.hasNext()) {
					lastRevision = revRev.next();
					if (currentUserRevision.getTimestampDate().isAfter(lastRevision.getTimestampDate())) {
						break;
					} else {
						index--;
					}
				}
				// split revisions into two parts
				if (index > 0) {
					List<WikipediaRevision> sub = revisions.subList(index, revisions.size());
					forkedRevisions = new LinkedList<WikipediaRevision>(sub);
					sub.clear();
					
					// calculate text of last revision
					
					String text = revisions.getFirst().getText();
					for (WikipediaRevision r : revisions) {
						if (r.getPatch() != null) {
							text = applyPatch(text, r.getPatch());
						}
					}
					revisions.getLast().setText(text);
					// calculate text of first forked revision
					WikipediaRevision ffr = forkedRevisions.getFirst();
					ffr.setText(applyPatch(text, ffr.getPatch()));
					ffr.setPatch(null);
				} else { // currentRevision is before all previous revisions
					forkedRevisions = new LinkedList<WikipediaRevision>(revisions);
					revisions = new LinkedList<WikipediaRevision>();
				}
				forkedDateTime = forkedRevisions.getFirst().getTimestampDate();
								
			}
			
			
			
			if (!revisions.isEmpty()) {
								
				WikipediaRevision previousRevision = revisions.getLast();
				
				LinkedList<Diff> diffs = getDiffs(previousRevision);
				LinkedList<diff_match_patch.Patch> patches;		    
			    try {
					patches = dmp.patch_make(previousRevision.getText(), diffs);
					
				} catch (StringIndexOutOfBoundsException e) {
					// occurred at a large reverse of "Spanish Armada" (15:54, 26 November 2006‎) 
					// skip previous revision
					log.error("StringIndexOutOfBoundsException: " + currentUserRevision.getPage().getTitle() + "; current revision: " + currentUserRevision.getTimestamp());
					revisions.removeLast();
					
					// calculate text of last revision
					String text = revisions.getFirst().getText();
					for (WikipediaRevision r : revisions) {
						if (r.getPatch() != null) {
							text = applyPatch(text, r.getPatch());
						}
					}

					previousRevision = revisions.getLast();
					previousRevision.setText(text);
					
					diffs = getDiffs(previousRevision);
				    patches = dmp.patch_make(previousRevision.getText(), diffs);					
				}
			    
			    currentUserRevision.setPatch(dmp.patch_toText(patches));
				
				noChars += currentUserRevision.getText().length();
				noCharsDiffs += currentUserRevision.getPatch().length();
				
				// overwrite parentId
				currentUserRevision.setParentId(previousRevision.getId());
				
				//clear text to save memory/storage
				if (previousRevision.getParentId() != null)
					previousRevision.setText(null);
			} else { // currentUserRevision is first revision of page
				currentUserRevision.setParentId(null);
			}
			revisions.add(currentUserRevision);
			
			
		}
	}
	
	private LinkedList<Diff> getDiffs(WikipediaRevision previousRevision) {
		// calculate diff and patch
		LinesToCharsResult a = diff_linesToChars(previousRevision.getText(), currentUserRevision.getText());
		String lineText1 = a.chars1;
		String lineText2 = a.chars2;
		List<String> lineArray = a.lineArray;
		LinkedList<Diff> diffs = dmp.diff_main(lineText1, lineText2, false);
		diff_charsToLines(diffs, lineArray);
						
	    if (diffs.size() > 2) {
	    	dmp.diff_cleanupSemantic(diffs);
	    	dmp.diff_cleanupEfficiency(diffs);
	    }
	    return diffs;
	}
	
	
	private String applyPatch(String text, String patch) {
		Object[] result = dmp.patch_apply(new LinkedList<diff_match_patch.Patch>(dmp.patch_fromText(patch)), text);
		return (String)result[0];
	}
		
	private void appendForkedRevisions() {
		WikipediaRevision previousRevision = revisions.getLast();
		WikipediaRevision forkedRevision = forkedRevisions.getFirst();
		// overwrite parentId
		forkedRevision.setParentId(previousRevision.getId());
		// set diff/create patches
		LinkedList<diff_match_patch.Patch> patches = dmp.patch_make(previousRevision.getText(), forkedRevision.getText());
		forkedRevision.setPatch(dmp.patch_toText(patches));
		
		//clear text to save memory/storage
		if (previousRevision.getParentId() != null)
			previousRevision.setText(null);
		
		if (forkedRevisions.size() > 1)
			forkedRevision.setText(null);
		
		revisions.addAll(forkedRevisions);
		forkedRevisions = new LinkedList<WikipediaRevision>();
		forkedDateTime = null;
	}
	
	
	/**
	 * copied from diff_match_patch (https://github.com/google/diff-match-patch/)
	 * 
	 * Split two texts into a list of strings.  Reduce the texts to a string of
	 * hashes where each Unicode character represents one line.
	 * @param text1 First string.
	 * @param text2 Second string.
	 * @return An object containing the encoded text1, the encoded text2 and
	 *     the List of unique strings.  The zeroth element of the List of
	 *     unique strings is intentionally blank.
	 */
	private static class LinesToCharsResult {
	    protected String chars1;
	    protected String chars2;
	    protected List<String> lineArray;

	    protected LinesToCharsResult(String chars1, String chars2,
	        List<String> lineArray) {
	      this.chars1 = chars1;
	      this.chars2 = chars2;
	      this.lineArray = lineArray;
	    }
	  }
	
	/**
	   * copied from diff_match_patch (https://github.com/google/diff-match-patch/)
	   * 
	   * Split two texts into a list of strings.  Reduce the texts to a string of
	   * hashes where each Unicode character represents one line.
	   * @param text1 First string.
	   * @param text2 Second string.
	   * @return An object containing the encoded text1, the encoded text2 and
	   *     the List of unique strings.  The zeroth element of the List of
	   *     unique strings is intentionally blank.
	   */
	  private LinesToCharsResult diff_linesToChars(String text1, String text2) {
	    List<String> lineArray = new ArrayList<String>();
	    Map<String, Integer> lineHash = new HashMap<String, Integer>();
	    // e.g. linearray[4] == "Hello\n"
	    // e.g. linehash.get("Hello\n") == 4

	    // "\x00" is a valid character, but various debuggers don't like it.
	    // So we'll insert a junk entry to avoid generating a null character.
	    lineArray.add("");

	    String chars1 = diff_linesToCharsMunge(text1, lineArray, lineHash);
	    String chars2 = diff_linesToCharsMunge(text2, lineArray, lineHash);
	    return new LinesToCharsResult(chars1, chars2, lineArray);
	  }


	  /**
	   * copied from diff_match_patch (https://github.com/google/diff-match-patch/)
	   * 
	   * Split a text into a list of strings.  Reduce the texts to a string of
	   * hashes where each Unicode character represents one line.
	   * @param text String to encode.
	   * @param lineArray List of unique strings.
	   * @param lineHash Map of strings to indices.
	   * @return Encoded string.
	   */
	  private String diff_linesToCharsMunge(String text, List<String> lineArray,
	                                        Map<String, Integer> lineHash) {
	    int lineStart = 0;
	    int lineEnd = -1;
	    String line;
	    StringBuilder chars = new StringBuilder();
	    // Walk the text, pulling out a substring for each line.
	    // text.split('\n') would would temporarily double our memory footprint.
	    // Modifying text would create many large strings to garbage collect.
	    while (lineEnd < text.length() - 1) {
	      lineEnd = text.indexOf('\n', lineStart);

	      if (lineEnd == -1) {
	        lineEnd = text.length() - 1;
	      }
	      line = text.substring(lineStart, lineEnd + 1);
	      lineStart = lineEnd + 1;

	      if (lineHash.containsKey(line)) {
	        chars.append(String.valueOf((char) (int) lineHash.get(line)));
	      } else {
	        lineArray.add(line);
	        lineHash.put(line, lineArray.size() - 1);
	        chars.append(String.valueOf((char) (lineArray.size() - 1)));
	      }
	    }
	    return chars.toString();
	  }
	  
	  /**
	   * copied from diff_match_patch (https://github.com/google/diff-match-patch/)
	   * 
	   * Rehydrate the text in a diff from a string of line hashes to real lines of
	   * text.
	   * @param diffs LinkedList of Diff objects.
	   * @param lineArray List of unique strings.
	   */
	  private void diff_charsToLines(LinkedList<Diff> diffs,
	                                  List<String> lineArray) {
	    StringBuilder text;
	    for (Diff diff : diffs) {
	      text = new StringBuilder();
	      for (int y = 0; y < diff.text.length(); y++) {
	        text.append(lineArray.get(diff.text.charAt(y)));
	      }
	      diff.text = text.toString();
	    }
	  }
       
}