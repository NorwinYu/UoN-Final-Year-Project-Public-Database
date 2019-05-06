package model.DTO;

import java.util.List;

public class SearchDTO {

    private String searchInput;
    private String tagId;
    private int pageNumb;

    private int psNextIndex; //naujai pridejau. cia is prepared statement koks bus sekantis parametro statomo indexas. uzdet get ir set
    private List<String> searchWords;

    public SearchDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public int getPsNextIndex() {
        return psNextIndex;
    }

    public List getSearchWords() {
        return searchWords;
    }

    public void setPsNextIndex(int psNextIndex) {
        this.psNextIndex = psNextIndex;
    }

    public void setSearchWords(List<String> searchWords) {
        this.searchWords = searchWords;
    }

    public SearchDTO(String s, String d) {
        super();
        searchInput = s;
        tagId = d;
    }

    public String getSearchInput() {

        return searchInput;
    }

    public void setSearchInput(String s) {

        searchInput = s;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String d) {

        tagId = d;
    }

    public int getPageNumb() {
        return pageNumb;
    }

    public void setPageNumb(int pageNumb) {
        this.pageNumb = pageNumb;
    }
    
}

