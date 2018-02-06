package eugene.com.newsrss.db.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;


public class NewsStationView implements Parcelable {
    @DrawableRes
    private int logo;
    @ColorInt
    private int colorPrimary;
    @ColorInt
    private int colorPrimaryDark;
    @ColorInt
    private int colorAccent;
    @ColorInt
    private int textColor;

    public NewsStationView(int logo, int[] colors) {
        this.logo = logo;
        this.colorPrimary = colors[0];
        this.colorPrimaryDark = colors[1];
        this.colorAccent = colors[2];
        this.textColor = colors[3];
    }

    public NewsStationView(int colorPrimary, int colorPrimaryDark, int colorAccent) {
        this.colorPrimary = colorPrimary;
        this.colorPrimaryDark = colorPrimaryDark;
        this.colorAccent = colorAccent;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public int getColorPrimary() {
        return colorPrimary;
    }

    public void setColorPrimary(int colorPrimary) {
        this.colorPrimary = colorPrimary;
    }

    public int getColorPrimaryDark() {
        return colorPrimaryDark;
    }

    public void setColorPrimaryDark(int colorPrimaryDark) {
        this.colorPrimaryDark = colorPrimaryDark;
    }

    public int getColorAccent() {
        return colorAccent;
    }

    public void setColorAccent(int colorAccent) {
        this.colorAccent = colorAccent;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.logo);
        dest.writeInt(this.colorPrimary);
        dest.writeInt(this.colorPrimaryDark);
        dest.writeInt(this.colorAccent);
        dest.writeInt(this.textColor);
    }

    protected NewsStationView(Parcel in) {
        this.logo = in.readInt();
        this.colorPrimary = in.readInt();
        this.colorPrimaryDark = in.readInt();
        this.colorAccent = in.readInt();
        this.textColor = in.readInt();
    }

    public static final Parcelable.Creator<NewsStationView> CREATOR = new Parcelable.Creator<NewsStationView>() {
        @Override
        public NewsStationView createFromParcel(Parcel source) {
            return new NewsStationView(source);
        }

        @Override
        public NewsStationView[] newArray(int size) {
            return new NewsStationView[size];
        }
    };
}
