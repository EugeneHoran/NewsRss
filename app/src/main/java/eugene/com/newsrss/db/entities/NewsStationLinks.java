package eugene.com.newsrss.db.entities;


import android.os.Parcel;
import android.os.Parcelable;

public class NewsStationLinks implements Parcelable {
    private String title;
    private String url;
    private boolean primary;
    private boolean checked;

    public NewsStationLinks(String title, String url, boolean primary) {
        this.title = title;
        this.url = url;
        this.primary = primary;
        this.checked = true;
    }

    public NewsStationLinks(String title, String url) {
        this.title = title;
        this.url = url;
        this.primary = false;
        this.checked = true;
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

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    protected NewsStationLinks(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
        this.checked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<NewsStationLinks> CREATOR = new Parcelable.Creator<NewsStationLinks>() {
        @Override
        public NewsStationLinks createFromParcel(Parcel source) {
            return new NewsStationLinks(source);
        }

        @Override
        public NewsStationLinks[] newArray(int size) {
            return new NewsStationLinks[size];
        }
    };
}
