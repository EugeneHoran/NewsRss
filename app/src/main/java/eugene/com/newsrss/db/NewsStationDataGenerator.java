package eugene.com.newsrss.db;

import java.util.ArrayList;
import java.util.List;

import eugene.com.newsrss.R;
import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.db.entities.NewsStationLinks;
import eugene.com.newsrss.db.entities.NewsStationView;
import eugene.com.newsrss.util.ColorUtil;

class NewsStationDataGenerator {
    private static final String URL_CNN_LATEST = "http://rss.cnn.com/rss/cnn_latest.rss";
    private static final String URL_CNN_TOP = "http://rss.cnn.com/rss/cnn_topstories.rss";
    private static final String URL_CNN_WORLD = "http://rss.cnn.com/rss/cnn_world.rss";
    private static final String URL_CNN_BUSINESS = "http://rss.cnn.com/rss/money_latest.rss";
    private static final String URL_CNN_POLITICS = "http://rss.cnn.com/rss/cnn_allpolitics.rss";

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
        newsList.add(cnn());
        // FOX
        newsList.add(fox());
        // NYT
        newsList.add(nyt());
        // BREITBART
        newsList.add(breitbart());
        // BBC
        newsList.add(bbc());
        // THE DAILY BEAST
        newsList.add(dailyBeast());
        // CNBC
        newsList.add(cnbc());
        return newsList;
    }

    private NewsStation cnn() {
        List<NewsStationLinks> links = new ArrayList<>();
        links.add(new NewsStationLinks(0, "Latest Stories", URL_CNN_LATEST, true));
        links.add(new NewsStationLinks(1, "Top Stories", URL_CNN_TOP));
        links.add(new NewsStationLinks(2, "World Stories", URL_CNN_WORLD));
        links.add(new NewsStationLinks(3, "Business", URL_CNN_BUSINESS));
        links.add(new NewsStationLinks(4, "Politics", URL_CNN_POLITICS));
        return new NewsStation("CNN",
                links,
                new NewsStationView(
                        R.drawable.logo_cnn,
                        ColorUtil.getColors("#C70005")
                ));
    }

    private NewsStation fox() {
        List<NewsStationLinks> links = new ArrayList<>();
        links.add(new NewsStationLinks(0, "Latest Stories", URL_FOX_LATEST, true));
        links.add(new NewsStationLinks(1, "Popular Stories", URL_FOX_POPULAR));
        return new NewsStation(
                "Fox News",
                links,
                new NewsStationView(
                        R.drawable.logo_fox,
                        ColorUtil.getColors("#003265")
                )
        );
    }

    private NewsStation nyt() {
        List<NewsStationLinks> links = new ArrayList<>();
        links.add(new NewsStationLinks(0, "Home Page Stories", URL_NYT_PRIMARY, true));
        links.add(new NewsStationLinks(1, "Most Viewed Stories", URL_NYT_SECONDARY));
        return new NewsStation(
                "New York Times",
                links,
                new NewsStationView(
                        R.drawable.logo_nyt,
                        ColorUtil.getColors("#FFFFFF")
                )
        );
    }

    private NewsStation breitbart() {
        List<NewsStationLinks> links = new ArrayList<>();
        links.add(new NewsStationLinks(0, "Top Stories", URL_BREITBART, true));
        return new NewsStation(
                "Breitbart",
                links,
                new NewsStationView(
                        R.drawable.logo_breitbart,
                        ColorUtil.getColors("#FF5202")
                )
        );
    }


    private NewsStation bbc() {
        List<NewsStationLinks> links = new ArrayList<>();
        links.add(new NewsStationLinks(0, "Top Stories", URL_BBC_PRIMARY, true));
        links.add(new NewsStationLinks(1, "World Stories", URL_BBC_SECONDARY));
        return new NewsStation(
                "BBC",
                links,
                new NewsStationView(
                        R.drawable.logo_bbc,
                        ColorUtil.getColors("#BB1919")
                )
        );
    }

    private NewsStation dailyBeast() {
        List<NewsStationLinks> links = new ArrayList<>();
        links.add(new NewsStationLinks(0, "Latest Stories", URL_THE_DAILY_BEAST, true));
        return new NewsStation(
                "The Daily Beast",
                links,
                new NewsStationView(
                        R.drawable.logo_daily_beast,
                        ColorUtil.getColors("#303030")
                )
        );
    }

    private NewsStation cnbc() {
        List<NewsStationLinks> links = new ArrayList<>();
        links.add(new NewsStationLinks(0, "Top Stories", URL_CNBC_PRIMARY, true));
        links.add(new NewsStationLinks(1, "US News", URL_CNBC_SECONDARY));
        return new NewsStation(
                "CNBC",
                links,
                new NewsStationView(
                        R.drawable.logo_cnbc,
                        ColorUtil.getColors("#146195")
                )
        );
    }
}
