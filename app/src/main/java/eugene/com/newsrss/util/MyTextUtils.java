package eugene.com.newsrss.util;


import android.text.TextUtils;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyTextUtils {

    public static String formatDate(String pubDate) {
        if (TextUtils.isEmpty(pubDate)) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss", Locale.ENGLISH);
        try {
            return DateUtils.getRelativeTimeSpanString(format.parse(pubDate).getTime()).toString();
        } catch (ParseException e) {
            return null;
        }
    }
}
