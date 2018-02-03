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
    }

    public AppExecutors getAppExecutors() {
        if (appExecutors == null) {
            appExecutors = new AppExecutors();
        }
        return appExecutors;
    }

    public NewsDatabase getNewsDatabase() {
        return NewsDatabase.getInstance(this, appExecutors);
    }

    public RssClient getTimesClient() {
        return RssClient.getInstance();
    }
}
