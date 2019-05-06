package wikipedia.parser;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import wikipedia.model.WikipediaPage;
import wikipedia.model.WikipediaRevision;
import wikipedia.model.WikipediaUser;

/**
 * 
 * The handler for the SAX-Parser.
 * 
 * @author zschache
 *
 */
public class PageHandler extends DefaultHandler {

    private final PageProcessor processor;
    private WikipediaPage page;
    private WikipediaRevision revision;
    private WikipediaUser user;
    private StringBuilder stringBuilder;
    
    private List<String> skippedPages;
    private boolean checkSkippedPages = false;
    
    public PageHandler(List<String> skippedPages, PageProcessor processor) {
        this.processor = processor;
        // check skipped pages only if it was not empty at initialization
        this.checkSkippedPages = !skippedPages.isEmpty();
        this.skippedPages = skippedPages;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    	
    	if (qName.equals("page")){
    		page = new WikipediaPage();
    	} else if (page != null && !page.isRedirecting()) {
    		switch (qName) {
    			case "redirect": {
    				page.setRedirecting(true);
    				break;
                }
    			case "revision": {
    				revision = new WikipediaRevision(page);
    				break;
    			}
    			case "contributor": {
    				if (attributes.getValue("deleted") == null) {
    					user = new WikipediaUser();
    				}
    				break;
    			}
    			case "title":
    			case "id":
    			case "parentid": 
    			case "timestamp": 
    			case "text":
    			case "ip": 
    			case "username": {
    				stringBuilder = new StringBuilder();
    				break;
    			}
    		}
    	}
    	
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
    	if (stringBuilder != null)
    		stringBuilder.append(ch,start, length); 
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    	if (page != null && !page.isRedirecting()){
    		switch (qName) {
	    		case "page": {
	    			processor.endPage(page);
	    			break;
	    		}
 				case "title": {
 					page.setTitle(stringBuilder.toString());
 					stringBuilder = null;
	 				break;
	 			}
	 			case "id": {
	 				if (user != null) {
	 					user.setId(stringBuilder.toString());
	 				} else if (revision != null){
	 					revision.setId(stringBuilder.toString());
	 				} else {
	 					page.setId(stringBuilder.toString());
	 					if (checkSkippedPages) {
	 						// check if page was skipped before
	 						if (skippedPages.contains(page.getId())) {
	 							skippedPages.remove(page.getId());
	 							processor.startPage(page, true);
	 						} else { 
	 							page = null;
	 						}
	 					} else { // normal processing
	 						processor.startPage(page, false);
	 					}
	 				}
	 				stringBuilder = null;
	                break;
	 			}
	 			case "ip": {
	 				if (revision != null)
	 					revision.setContributorIp(stringBuilder.toString());
	 				user = null;
	 				stringBuilder = null;
	                break;
	 			}
	 			case "username": {
	 				if (user != null)
	 					user.setName(stringBuilder.toString());
	 				stringBuilder = null;
	                break;
	 			}
	 			case "timestamp": {
	 				if (revision != null)
	 					revision.setTimestamp(stringBuilder.toString());
	 				stringBuilder = null;
	                break;
	 			}
	 			case "parentid": {
	 				if (revision != null)
	 					revision.setParentId(stringBuilder.toString());
	 				stringBuilder = null;
	                break;
	 			}
	 			case "text": {
	 				if (revision != null)
	 					revision.setText(stringBuilder.toString());
	 				stringBuilder = null;
	                break;
	 			}
	 			case "contributor": {
	 				if (revision != null && user != null) {
	 					revision.setContributor(user);
	 				}
	 				user = null;
	 				break;
	 			}
	 			case "revision": {
	 				processor.process(revision);
	 				revision = null;
	 			}
    		}
    	}
    }
    
    @Override
    public void endDocument() throws SAXException {
    	processor.endDocument();
    }

}
