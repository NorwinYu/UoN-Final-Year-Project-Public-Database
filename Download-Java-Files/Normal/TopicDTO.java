package model.DTO;

import model.beans.TopicBean;

import java.util.List;

public class TopicDTO {

    private List<TopicBean> topicsList;
    private String tagId;
    private String searchInput;
    private int pageNumb;
    private int numbOfRecords;
    private String tabName;
    private ErrorDTO errorDTO;


    public TopicDTO(List<TopicBean> topicsList, String tagId, String searchInput, int pageNumb, int numbOfRecords, String tabName) {
        this.topicsList = topicsList;
        this.tagId = tagId;
        this.searchInput = searchInput;
        this.pageNumb = pageNumb;
        this.numbOfRecords = numbOfRecords;
        this.tabName = tabName;
    }

    public TopicDTO(List<TopicBean> topicsList, String tagId, String searchInput, int pageNumb, int numbOfRecords) {
        this.topicsList = topicsList;
        this.tagId = tagId;
        this.searchInput = searchInput;
        this.pageNumb = pageNumb;
        this.numbOfRecords = numbOfRecords;
    }

    public TopicDTO(String tagId, String searchInput) {
         this.tagId = tagId;
        this.searchInput = searchInput;
    }


    public TopicDTO(String tagId, String searchInput, ErrorDTO errorDTO ) {
        this.tagId = tagId;
        this.searchInput = searchInput;
        this.errorDTO = errorDTO;
    }

    public List<TopicBean> getTopicsList() {
        return topicsList;
    }

    public void setTopicsList(List<TopicBean> topicsList) {
        this.topicsList = topicsList;
    }

    public String getSearchInput() {
        return searchInput;
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }

    public int getPageNumb() {
        return pageNumb;
    }

    public void setPageNumb(int pageNumb) {
        this.pageNumb = pageNumb;
    }

    public int getNumbOfRecords() {
        return numbOfRecords;
    }

    public void setNumbOfRecords(int numbOfRecords) {
        this.numbOfRecords = numbOfRecords;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }
}
