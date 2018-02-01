
package eugene.com.newsrss.model;

import com.google.gson.annotations.SerializedName;

public class Enclosure {

    @SerializedName("link")
    private String link;

    @SerializedName("thumbnail")
    private String thumbnail;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
