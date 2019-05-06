package wikipedia.parser;

import wikipedia.model.WikipediaPage;
import wikipedia.model.WikipediaRevision;

public interface PageProcessor {
		
	void startPage(WikipediaPage page, boolean orderByDate);
    void process(WikipediaRevision revision);
    void endPage(WikipediaPage page);
    void endDocument();
}