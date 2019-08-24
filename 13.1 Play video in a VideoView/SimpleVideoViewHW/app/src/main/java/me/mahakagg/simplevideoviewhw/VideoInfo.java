package me.mahakagg.simplevideoviewhw;

// helper class for videos
public class VideoInfo {

    private String title; // video title
    private String url; // video URL

    // constructor, getter and setters for member variables
    public VideoInfo(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
