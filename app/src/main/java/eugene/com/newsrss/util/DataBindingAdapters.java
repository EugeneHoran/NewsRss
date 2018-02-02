package eugene.com.newsrss.util;

import android.databinding.BindingAdapter;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

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

    @BindingAdapter("backgroundColor")
    public static void setBackgroundColor(View view, int color) {
        view.setBackgroundColor(color);
    }

    @BindingAdapter("checkBoxColor")
    public static void setCheckBoxColor(AppCompatCheckBox view, int color) {
        if (Build.VERSION.SDK_INT >= 23) {
            view.getButtonDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }
}
