package eugene.com.newsrss.ui.rss;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import eugene.com.newsrss.RssApplication;
import eugene.com.newsrss.db.NewsDatabase;
import eugene.com.newsrss.db.entities.NewsStation;

public class RssParentViewModel extends AndroidViewModel {
    private MediatorLiveData<List<NewsStation>> mediatorNewsStationList = new MediatorLiveData<>();
    private MutableLiveData<List<String>> navigationMenuItem = new MutableLiveData<>();
    private MutableLiveData<int[]> pagerLogos = new MutableLiveData<>();

    public RssParentViewModel(@NonNull Application application) {
        super(application);
        NewsDatabase newsDatabase = ((RssApplication) application).getNewsDatabase();
        mediatorNewsStationList.addSource(newsDatabase.getNewsStationDao().getNewsStations(), newsStationList -> {
            if (newsStationList != null && newsStationList.size() > 0) {
                initNewsData(newsStationList);
            }
        });
    }

    private void initNewsData(List<NewsStation> newsList) {
        // init logos and navigation menu items
        int[] logos = new int[newsList.size()];
        List<String> menuItemList = new ArrayList<>();
        for (int i = 0; i < newsList.size(); i++) {
            logos[i] = newsList.get(i).getNewsStationView().getLogo();
            menuItemList.add(newsList.get(i).getTitle());
        }
        pagerLogos.setValue(logos);
        navigationMenuItem.setValue(menuItemList);
        // init rss view pager
        mediatorNewsStationList.setValue(newsList);
    }

    LiveData<List<NewsStation>> getNewsStationList() {
        return mediatorNewsStationList;
    }

    LiveData<int[]> getPagerLogos() {
        return pagerLogos;
    }

    LiveData<List<String>> getNavigationMenuItem() {
        return navigationMenuItem;
    }
}
