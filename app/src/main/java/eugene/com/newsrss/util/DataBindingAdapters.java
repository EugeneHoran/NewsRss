package eugene.com.newsrss.util;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import eugene.com.newsrss.R;
import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.db.entities.NewsStationView;

public class DataBindingAdapters {


    @BindingAdapter("imageUrl")
    public static void bindImage(ImageView imageView, String imageReference) {
        if (TextUtils.isEmpty(imageReference)) {
            imageView.setVisibility(View.GONE);
            return;
        }
        Glide.with(imageView.getContext()).load(imageReference)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    @BindingAdapter("setVisible")
    public static void bindVisibility(View view, Boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("textColor")
    public static void setTextColor(View view, int color) {
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(color);
        }
    }

    @BindingAdapter("newsDrawableLift")
    public static void setBackgroundColor(CheckBox view, NewsStation newsStation) {
        Drawable mDrawable = view.getCompoundDrawables()[0];
        if (mDrawable != null) {
            mDrawable.mutate();
            if (newsStation.isShow()) {
                if (newsStation.getTitle().equalsIgnoreCase("New York Times")) {
                    mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN));
                } else {
                    mDrawable.setColorFilter(new PorterDuffColorFilter(newsStation.getNewsStationView().getColorPrimary(), PorterDuff.Mode.SRC_IN));
                }
            } else {
                mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor("#9E9E9E"), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    @BindingAdapter("checkBoxColor")
    public static void setCheckBoxColor(AppCompatCheckBox view, int color) {
        if (Build.VERSION.SDK_INT >= 23) {
            view.getButtonDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }
}
