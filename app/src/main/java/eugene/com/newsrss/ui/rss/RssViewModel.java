package eugene.com.newsrss.ui.rss;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import eugene.com.livelib.ApiResponse;
import eugene.com.newsrss.RssApplication;
import eugene.com.newsrss.api.RssService;
import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.model.RssResponse;

public class RssViewModel extends AndroidViewModel {
    private MediatorLiveData<ApiResponse<RssResponse>> apiPrimaryResponse;
    private MediatorLiveData<ApiResponse<RssResponse>> apiSecondaryResponse;

    public RssViewModel(@NonNull Application application, NewsStation news) {
        super(application);
        RssService service = ((RssApplication) application).getTimesClient().create();
        apiPrimaryResponse = new MediatorLiveData<>();
        apiSecondaryResponse = new MediatorLiveData<>();
        apiPrimaryResponse.addSource(service.getNews(news.getUrlPrimary()), primaryResponse ->
                apiPrimaryResponse.setValue(primaryResponse));
        if (!TextUtils.isEmpty(news.getUrlSecondary())) {
            apiSecondaryResponse.addSource(service.getNews(news.getUrlSecondary()), secondaryResponse ->
                    apiSecondaryResponse.setValue(secondaryResponse));
        }
    }

    LiveData<ApiResponse<RssResponse>> getPrimaryNews() {
        return apiPrimaryResponse;
    }

    LiveData<ApiResponse<RssResponse>> getSecondaryNews() {
        return apiSecondaryResponse;
    }
}
