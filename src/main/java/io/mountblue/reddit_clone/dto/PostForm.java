package io.mountblue.reddit_clone.dto;

import org.springframework.web.multipart.MultipartFile;

public class PostForm {
    private int subredditId;
    private String title;
    private String content;
    private MultipartFile image;

    public PostForm() {
    }

    public int getSubredditId() {
        return subredditId;
    }

    public void setSubredditId(int subredditId) {
        this.subredditId = subredditId;
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "PostForm{" +
                "subredditId=" + subredditId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", image=" + image +
                '}';
    }
}
