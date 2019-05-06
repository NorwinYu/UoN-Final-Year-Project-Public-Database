package model.beans;

public class TopicBean {
    private int id;
    private String title;

    public TopicBean() {
    }

    public TopicBean(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }
}
