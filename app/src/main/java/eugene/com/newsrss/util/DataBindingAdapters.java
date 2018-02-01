package eugene.com.newsrss.util;

import android.databinding.BindingAdapter;
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
    public static void setTextColor(TextView view, int color) {
        view.setTextColor(color);
    }

}
