package eugene.com.newsrss.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.UUID;

@Entity(tableName = "news_stations")
public class NewsStation implements Parcelable {
    @NonNull
    @PrimaryKey
    private String id;
    private boolean show;
    private String title;
    private List<NewsStationLinks> newsStationLinks;
    private String urlPrimary;
    private String urlPrimaryTitle;
    private String urlSecondary = null;
    private String urlSecondaryTitle = null;
    private NewsStationView newsStationView;

    public NewsStation() {
    }

    @Ignore
    public NewsStation(
            String title,
            List<NewsStationLinks> newsStationLinks,
            String urlLatestNews,
            String urlSecondary,
            String urlPrimaryTitle,
            String urlSecondaryTitle,
            NewsStationView newsStationView
    ) {
        this.id = UUID.randomUUID().toString();
        this.show = true;
        this.title = title;
        this.newsStationLinks = newsStationLinks;
        this.urlPrimary = urlLatestNews;
        this.urlSecondary = urlSecondary;
        this.urlPrimaryTitle = urlPrimaryTitle;
        this.urlSecondaryTitle = urlSecondaryTitle;
        this.newsStationView = newsStationView;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<NewsStationLinks> getNewsStationLinks() {
        return newsStationLinks;
    }

    public void setNewsStationLinks(List<NewsStationLinks> newsStationLinks) {
        this.newsStationLinks = newsStationLinks;
    }

    public String getUrlPrimary() {
        return urlPrimary;
    }

    public void setUrlPrimary(String urlPrimary) {
        this.urlPrimary = urlPrimary;
    }

    public String getUrlPrimaryTitle() {
        return urlPrimaryTitle;
    }

    public void setUrlPrimaryTitle(String urlPrimaryTitle) {
        this.urlPrimaryTitle = urlPrimaryTitle;
    }

    public String getUrlSecondary() {
        return urlSecondary;
    }

    public void setUrlSecondary(String urlSecondary) {
        this.urlSecondary = urlSecondary;
    }

    public String getUrlSecondaryTitle() {
        return urlSecondaryTitle;
    }

    public void setUrlSecondaryTitle(String urlSecondaryTitle) {
        this.urlSecondaryTitle = urlSecondaryTitle;
    }

    public NewsStationView getNewsStationView() {
        return newsStationView;
    }

    public void setNewsStationView(NewsStationView newsStationView) {
        this.newsStationView = newsStationView;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeByte(this.show ? (byte) 1 : (byte) 0);
        dest.writeString(this.title);
        dest.writeTypedList(this.newsStationLinks);
        dest.writeString(this.urlPrimary);
        dest.writeString(this.urlPrimaryTitle);
        dest.writeString(this.urlSecondary);
        dest.writeString(this.urlSecondaryTitle);
        dest.writeParcelable(this.newsStationView, flags);
    }

    protected NewsStation(Parcel in) {
        this.id = in.readString();
        this.show = in.readByte() != 0;
        this.title = in.readString();
        this.newsStationLinks = in.createTypedArrayList(NewsStationLinks.CREATOR);
        this.urlPrimary = in.readString();
        this.urlPrimaryTitle = in.readString();
        this.urlSecondary = in.readString();
        this.urlSecondaryTitle = in.readString();
        this.newsStationView = in.readParcelable(NewsStationView.class.getClassLoader());
    }

    public static final Creator<NewsStation> CREATOR = new Creator<NewsStation>() {
        @Override
        public NewsStation createFromParcel(Parcel source) {
            return new NewsStation(source);
        }

        @Override
        public NewsStation[] newArray(int size) {
            return new NewsStation[size];
        }
    };
}
