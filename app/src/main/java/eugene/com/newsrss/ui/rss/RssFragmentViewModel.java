package eugene.com.newsrss.ui.rss;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import eugene.com.livelib.LiveCall;
import eugene.com.newsrss.RssApplication;
import eugene.com.newsrss.api.RssService;
import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.db.entities.NewsStationLinks;
import eugene.com.newsrss.model.NewsDisplayList;

public class RssFragmentViewModel extends AndroidViewModel {
    private List<NewsDisplayList> newsDisplayLists = new ArrayList<>();
    private final MutableLiveData<String> linkUrl = new MutableLiveData<>();
    private MediatorLiveData<List<NewsDisplayList>> displayListMediatorLiveData = new MediatorLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    // TODO convert calls to asynchronous
    public RssFragmentViewModel(@NonNull Application application, NewsStation news) {
        super(application);
        RssApplication app = (RssApplication) application;
        RssService service = app.getTimesClient().create();
        List<NewsStationLinks> selectedLinkList = news.getSelectedNewsStationLinks();
        loading.setValue(true);
        if (selectedLinkList.size() > 0) {
            linkUrl.setValue(selectedLinkList.get(newsDisplayLists.size()).getUrl());
            displayListMediatorLiveData.addSource(Transformations.switchMap(linkUrl, url ->
                    new LiveCall<>(service.getNews(url))), rssResponseResource -> {
                if (rssResponseResource != null && rssResponseResource.data != null) {
                    newsDisplayLists.add(new NewsDisplayList(
                            news.getSelectedNewsStationLinks().get(newsDisplayLists.size()).getTitle(),
                            rssResponseResource.data.getItems(),
                            news.getNewsStationView().getTextColor(),
                            newsDisplayLists.size() == 0));
                    if (newsDisplayLists.size() < selectedLinkList.size()) {
                        linkUrl.setValue(selectedLinkList.get(newsDisplayLists.size()).getUrl());
                    } else {
                        // Temp solution to put move primary news to bottom
                        if (newsDisplayLists.size() > 1) {
                            NewsDisplayList items = newsDisplayLists.get(0);
                            newsDisplayLists.remove(0);
                            newsDisplayLists.add(items);
                        }
                        displayListMediatorLiveData.setValue(newsDisplayLists);
                        loading.setValue(false);
                    }
                }
            });
        }
    }

    LiveData<List<NewsDisplayList>> getNewsDisplayList() {
        return displayListMediatorLiveData;
    }

    LiveData<Boolean> isLoading() {
        return loading;
    }
}
