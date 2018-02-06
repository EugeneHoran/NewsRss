package eugene.com.newsrss.ui.select.sources;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import eugene.com.livelib.AppExecutors;
import eugene.com.newsrss.RssApplication;
import eugene.com.newsrss.db.dao.NewsStationDao;
import eugene.com.newsrss.db.entities.NewsStation;


public class NewsSourcesFragmentViewModel extends AndroidViewModel {

    private AppExecutors appExecutors;
    private NewsStationDao dao;
    private LiveData<List<NewsStation>> newsStationList;

    public NewsSourcesFragmentViewModel(@NonNull Application application) {
        super(application);
        RssApplication app = (RssApplication) application;
        appExecutors = app.getAppExecutors();
        dao = app.getNewsDatabase().getNewsStationDao();
        newsStationList = dao.getNewsStations();
    }

    LiveData<List<NewsStation>> getNewsStationList() {
        return newsStationList;
    }

    void updateStation(NewsStation newsStation) {
        appExecutors.diskIO().execute(() ->
                dao.update(new NewsStation(newsStation)));
    }
}
