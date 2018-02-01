package eugene.com.newsrss.util;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.ui.rss.RssViewModel;


public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Application application;
    private final NewsStation news;

    public ViewModelFactory(Application application, NewsStation news) {
        this.news = news;
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RssViewModel.class)) {
            return (T) new RssViewModel(application, news);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
