package eugene.com.newsrss.ui.rss;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import eugene.com.livelib.LiveCall;
import eugene.com.livelib.Resource;
import eugene.com.newsrss.RssApplication;
import eugene.com.newsrss.api.RssService;
import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.model.RssResponse;

public class RssFragmentViewModel extends AndroidViewModel {
    private LiveData<Resource<RssResponse>> primaryData;
    private LiveData<Resource<RssResponse>> secondaryData;

    public RssFragmentViewModel(@NonNull Application application, NewsStation news) {
        super(application);
        RssApplication app = (RssApplication) application;
        RssService service = app.getTimesClient().create();
        primaryData = new LiveCall<>(service.getNews(news.getUrlPrimary()));
        if (!TextUtils.isEmpty(news.getUrlSecondary())) {
            secondaryData = new LiveCall<>(service.getNews(news.getUrlSecondary()));
        }
    }

    LiveData<Resource<RssResponse>> getPrimaryData() {
        return primaryData;
    }

    LiveData<Resource<RssResponse>> getSecondaryData() {
        return secondaryData;
    }
}
