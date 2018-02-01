
package eugene.com.newsrss.model;

import android.arch.persistence.room.Ignore;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item {

    @SerializedName("title")
    private String title;
    @SerializedName("pubDate")
    private String pubDate;
    @SerializedName("link")
    private String link;
    @SerializedName("guid")
    private String guid;
    @SerializedName("author")
    private String author;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("description")
    private String description;
    @SerializedName("content")
    private String content;
    @SerializedName("enclosure")
    private Enclosure enclosure;
    @SerializedName("categories")
    private List<String> categories = null;

    @Ignore
    public String getImageUrl() {
        String url = null;
        if (enclosure.getLink() == null && enclosure.getThumbnail() == null) {
            url = null;
        } else {
            if (enclosure.getThumbnail() != null) {
                url = enclosure.getThumbnail();
            }
            if (enclosure.getLink() != null) {
                url = enclosure.getLink();
            }
        }
        return url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        if (description.contains("<")) {
            String[] des = description.split("<");
            return des[0].trim();
        }
        return description.trim();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Enclosure getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

}
