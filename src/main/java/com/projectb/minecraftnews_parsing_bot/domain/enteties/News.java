package com.projectb.minecraftnews_parsing_bot.domain.enteties;

import java.awt.image.BufferedImage;

public class News {
    public News(String title, String content, BufferedImage photo) {
        this.title = title;
        this.content = content;
        this.photo = photo;

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

    public BufferedImage getPhoto() {
        return photo;
    }

    public void setPhotoUrl(BufferedImage photoUrl) {
        this.photo = photoUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String title;
    private String content;
    private BufferedImage photo;
    private String url;

    // Конструктор, геттери та сеттери
}

