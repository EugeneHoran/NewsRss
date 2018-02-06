package eugene.com.newsrss.db.entities;


import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

public class NewsStationLinks implements Parcelable {
    private int position;
    private String title;
    private String url;
    private boolean primary;
    private boolean checked;

    @Ignore
    @Override
    public String toString() {
        return "NewsStationLinks{" +
                "position=" + position +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", primary=" + primary +
                ", checked=" + checked +
                '}';
    }

    @Ignore
    public NewsStationLinks(int position, String title, String url, boolean primary) {
        this.position = position;
        this.title = title;
        this.url = url;
        this.primary = primary;
        this.checked = true;
    }

    @Ignore
    public NewsStationLinks(int position, String title, String url) {
        this.position = position;
        this.title = title;
        this.url = url;
        this.primary = false;
        this.checked = true;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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
        dest.writeInt(this.position);
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeByte(this.primary ? (byte) 1 : (byte) 0);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    protected NewsStationLinks(Parcel in) {
        this.position = in.readInt();
        this.title = in.readString();
        this.url = in.readString();
        this.primary = in.readByte() != 0;
        this.checked = in.readByte() != 0;
    }

    public static final Creator<NewsStationLinks> CREATOR = new Creator<NewsStationLinks>() {
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
