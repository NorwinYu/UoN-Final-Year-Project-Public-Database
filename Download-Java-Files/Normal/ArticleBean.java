package model.beans;

public class ArticleBean {

    private String example;
    private String title;

    public ArticleBean(String example, String title) {
        this.example = example;
        this.title = title;
    }

    public String getExample() {
        return example;
    }

    public String getTitle() {
        return title;
    }

}
