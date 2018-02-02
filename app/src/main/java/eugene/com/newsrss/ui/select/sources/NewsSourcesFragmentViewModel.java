package eugene.com.newsrss.ui.select.sources;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import eugene.com.newsrss.RssApplication;
import eugene.com.newsrss.db.entities.NewsStation;


public class NewsSourcesFragmentViewModel extends AndroidViewModel {
    private LiveData<List<NewsStation>> newsStationList;

    public NewsSourcesFragmentViewModel(@NonNull Application application) {
        super(application);
        RssApplication app = (RssApplication) application;
        newsStationList = app.getNewsDatabase().getNewsStationDao().getNewsStations();
    }

    LiveData<List<NewsStation>> getNewsStationList() {
        return newsStationList;
    }
}
