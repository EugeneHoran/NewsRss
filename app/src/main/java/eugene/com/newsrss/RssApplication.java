package eugene.com.newsrss;

import android.app.Application;

import eugene.com.livelib.AppExecutors;
import eugene.com.newsrss.api.RssClient;
import eugene.com.newsrss.db.NewsDatabase;

public class RssApplication extends Application {
    private AppExecutors appExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        appExecutors = new AppExecutors();
    }

    public AppExecutors getAppExecutors() {
        return appExecutors;
    }

    public NewsDatabase getNewsDatabase() {
        return NewsDatabase.getInstance(this, appExecutors);
    }

    public RssClient getTimesClient() {
        return RssClient.getInstance();
    }
}
