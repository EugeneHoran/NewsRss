package eugene.com.newsrss.model;

import java.util.List;

public class NewsDisplayList {
    private String title;

    private List<Item> articles;

    private int textColor;

    private boolean isPrimary;

    public NewsDisplayList(String title, List<Item> articles, int textColor, boolean isPrimary) {
        this.title = title;
        this.articles = articles;
        this.textColor = textColor;
        this.isPrimary = isPrimary;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Item> getArticles() {
        return articles;
    }

    public void setArticles(List<Item> articles) {
        this.articles = articles;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }
}
