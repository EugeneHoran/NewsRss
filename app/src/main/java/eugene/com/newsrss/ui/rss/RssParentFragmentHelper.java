package eugene.com.newsrss.ui.rss;

import android.animation.ArgbEvaluator;

import java.util.ArrayList;
import java.util.List;

import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.db.entities.NewsStationView;

public class RssParentFragmentHelper {
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private List<NewsStation> newsStationList = new ArrayList<>();

    public RssParentFragmentHelper() {
    }

    public void setList(List<NewsStation> newsStationList) {
        this.newsStationList.clear();
        this.newsStationList.addAll(newsStationList);
    }

    public NewsStationView getColorsFormatted(int adapterCount, int position, float positionOffset) {
        if (newsStationList == null) {
            return null;
        }
        NewsStationView colorsNews;
        if (position < (adapterCount - 1) && position < (newsStationList.size() - 1)) {
            NewsStationView newsStationView = newsStationList.get(position).getNewsStationView();
            NewsStationView newsStationViewNext = newsStationList.get(position + 1).getNewsStationView();
            colorsNews = new NewsStationView(
                    (Integer) argbEvaluator.evaluate(positionOffset, newsStationView.getColorPrimary(), newsStationViewNext.getColorPrimary()),
                    (Integer) argbEvaluator.evaluate(positionOffset, newsStationView.getColorPrimaryDark(), newsStationViewNext.getColorPrimaryDark()),
                    (Integer) argbEvaluator.evaluate(positionOffset, newsStationView.getColorAccent(), newsStationViewNext.getColorAccent())
            );
        } else {
            NewsStationView newsStationView = newsStationList.get(newsStationList.size() - 1).getNewsStationView();
            colorsNews = new NewsStationView(
                    newsStationView.getColorPrimary(),
                    newsStationView.getColorPrimaryDark(),
                    newsStationView.getColorAccent()
            );
        }
        return colorsNews;
    }
}
