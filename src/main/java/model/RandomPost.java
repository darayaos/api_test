package model;

public class RandomPost {
    public RandomPost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    private String title;
    private String content;
    public String getName() {
        return title;
    }
    public void setName(String title) {
        this.title = title;
    }
    public String getComment() {
        return content;
    }
    public void setComment(String content) {
        this.content = content;
    }

}