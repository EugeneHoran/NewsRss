package eugene.com.newsrss.db;

import java.util.ArrayList;
import java.util.List;

import eugene.com.newsrss.R;
import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.db.entities.NewsStationView;
import eugene.com.newsrss.util.ColorUtil;

class NewsStationDataGenerator {
    private static final String URL_CNN_LATEST = "http://rss.cnn.com/rss/cnn_latest.rss";
    private static final String URL_CNN_TOP = "http://rss.cnn.com/rss/cnn_topstories.rss";
    private static final String URL_FOX_LATEST = "http://feeds.foxnews.com/foxnews/latest?format=xml";
    private static final String URL_FOX_POPULAR = "http://feeds.foxnews.com/foxnews/most-popular?format=xml";
    private static final String URL_BBC_PRIMARY = "http://feeds.bbci.co.uk/news/rss.xml";
    private static final String URL_BBC_SECONDARY = "http://feeds.bbci.co.uk/news/world/rss.xml";
    private static final String URL_NYT_PRIMARY = "http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml";
    private static final String URL_NYT_SECONDARY = "http://rss.nytimes.com/services/xml/rss/nyt/MostViewed.xml";
    private static final String URL_CNBC_PRIMARY = "https://www.cnbc.com/id/100003114/device/rss/rss.html";
    private static final String URL_CNBC_SECONDARY = "https://www.cnbc.com/id/100727362/device/rss/rss.html";
    private static final String URL_BREITBART = "http://feeds.feedburner.com/breitbart";
    private static final String URL_THE_DAILY_BEAST = "http://rss.cnn.com/rss/cnn_latest.rss";

    List<NewsStation> getInitNewsStationList() {
        List<NewsStation> newsList = new ArrayList<>();
        // CNN
        newsList.add(new NewsStation(
                "CNN",
                URL_CNN_LATEST,
                URL_CNN_TOP,
                "Latest Stories",
                "Top Stories",
                new NewsStationView(
                        R.drawable.logo_cnn,
                        ColorUtil.getColors("#C70005")
                )
        ));
        // FOX
        newsList.add(new NewsStation(
                "Fox News",
                URL_FOX_LATEST,
                URL_FOX_POPULAR,
                "Latest Stories",
                "Popular Stories",
                new NewsStationView(
                        R.drawable.logo_fox,
                        ColorUtil.getColors("#003265")
                )
        ));
        // NYT
        newsList.add(new NewsStation(
                "New York Times",
                URL_NYT_PRIMARY,
                URL_NYT_SECONDARY,
                "Home Page Stories",
                "Most Viewed Stories",
                new NewsStationView(
                        R.drawable.logo_nyt,
                        ColorUtil.getColors("#FFFFFF")
                )
        ));
        // BREITBART
        newsList.add(new NewsStation(
                "Breitbart",
                URL_BREITBART,
                null,
                "Top Stories",
                null,
                new NewsStationView(
                        R.drawable.logo_breitbart,
                        ColorUtil.getColors("#FF5202")
                )
        ));
        // BBC
        newsList.add(new NewsStation(
                "BBC",
                URL_BBC_PRIMARY,
                URL_BBC_SECONDARY,
                "Top Stories",
                "World Stories",
                new NewsStationView(
                        R.drawable.logo_bbc,
                        ColorUtil.getColors("#BB1919")
                )
        ));
        // THE DAILY BEAST
        newsList.add(new NewsStation(
                "The Daily Beast",
                URL_THE_DAILY_BEAST,
                null,
                "Latest Stories",
                null,
                new NewsStationView(
                        R.drawable.logo_daily_beast,
                        ColorUtil.getColors("#303030")
                )
        ));
        // CNBC
        newsList.add(new NewsStation(
                "CNBC",
                URL_CNBC_PRIMARY,
                URL_CNBC_SECONDARY,
                "Top Stories",
                "US News",
                new NewsStationView(
                        R.drawable.logo_cnbc,
                        ColorUtil.getColors("#146195")
                )
        ));
        return newsList;
    }
}
