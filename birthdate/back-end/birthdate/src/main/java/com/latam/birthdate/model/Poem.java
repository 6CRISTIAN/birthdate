package com.latam.birthdate.model;

public class Poem {
    private String title;
    private String content;
    private String url;
    private Poet poet;

    public Poem() {
    }

    public Poem(String title, String content, String url, Poet poet) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.poet = poet;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Poet getPoet() {
        return poet;
    }

    public void setPoet(Poet poet) {
        this.poet = poet;
    }
}
