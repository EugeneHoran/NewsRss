package eugene.com.newsrss.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
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
    private NewsStationView newsStationView;
    @Ignore
    private int position;

    @Ignore
    public int getPosition() {
        return position;
    }

    @Ignore
    public void setPosition(int position) {
        this.position = position;
    }

    public NewsStation() {
    }

    @Ignore
    public NewsStation(NewsStation newsStation) {
        this.id = newsStation.getId();
        this.show = !newsStation.isShow();
        this.title = newsStation.getTitle();
        this.newsStationLinks = newsStation.getNewsStationLinks();
        this.newsStationView = newsStation.getNewsStationView();
    }

    @Ignore
    public NewsStation(NewsStation newsStation, int position) {
        this.id = newsStation.getId();
        this.show = !newsStation.isShow();
        this.title = newsStation.getTitle();
        this.newsStationLinks = newsStation.getNewsStationLinks();
        this.newsStationLinks.get(position).setChecked(!this.newsStationLinks.get(position).isChecked());
        this.newsStationView = newsStation.getNewsStationView();
    }

    @Ignore
    public NewsStation(
            String title,
            List<NewsStationLinks> newsStationLinks,
            NewsStationView newsStationView
    ) {
        this.id = UUID.randomUUID().toString();
        this.show = true;
        this.title = title;
        this.newsStationLinks = newsStationLinks;
        this.newsStationView = newsStationView;
    }

    @Ignore
    public List<NewsStationLinks> getSelectedNewsStationLinks() {
        List<NewsStationLinks> selectedList = new ArrayList<>();
        for (NewsStationLinks newsStationLink : newsStationLinks) {
            if (newsStationLink.isChecked()) {
                selectedList.add(newsStationLink);
            }
        }
        return selectedList;
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
        dest.writeParcelable(this.newsStationView, flags);
    }

    protected NewsStation(Parcel in) {
        this.id = in.readString();
        this.show = in.readByte() != 0;
        this.title = in.readString();
        this.newsStationLinks = in.createTypedArrayList(NewsStationLinks.CREATOR);
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
