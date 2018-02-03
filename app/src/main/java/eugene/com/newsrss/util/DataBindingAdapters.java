package eugene.com.newsrss.util;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import eugene.com.newsrss.db.entities.NewsStation;

public class DataBindingAdapters {


    @BindingAdapter("imageUrl")
    public static void bindImage(ImageView imageView, String imageReference) {
        if (TextUtils.isEmpty(imageReference)) {
            imageView.setVisibility(View.GONE);
            return;
        }
        GlideApp.with(imageView.getContext()).load(imageReference)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    @BindingAdapter("setVisible")
    public static void bindVisibility(View view, Boolean show) {
        if (show == null) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(show ? View.VISIBLE : View.GONE);
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
}
