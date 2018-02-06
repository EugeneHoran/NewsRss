package eugene.com.newsrss.ui.rss;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eugene.com.newsrss.R;
import eugene.com.newsrss.databinding.FragmentRssFeedBinding;
import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.ui.interfaces.RssLinkCallbacks;
import eugene.com.newsrss.ui.rss.adapters.RssFeedRecyclerAdapter;
import eugene.com.newsrss.util.ViewModelFactory;

public class RssFragment extends Fragment implements RssLinkCallbacks {
    private static final String ARG_RSS_NEWS = "args_rss_news";

    public RssFragment() {
    }

    public static RssFragment newInstance(NewsStation news) {
        RssFragment fragment = new RssFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RSS_NEWS, news);
        fragment.setArguments(args);
        return fragment;
    }

    private NewsStation news;
    private RssFragmentViewModel model;
    private RssFeedRecyclerAdapter adapter;
    private FragmentRssFeedBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null && getArguments() != null) {
            news = getArguments().getParcelable(ARG_RSS_NEWS);
            model = ViewModelProviders.of(this, new ViewModelFactory((Application) getActivity().getApplicationContext(), news)).get(RssFragmentViewModel.class);
            adapter = new RssFeedRecyclerAdapter(this, news.getTitle());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rss_feed, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recycler.setAdapter(adapter);
        observeAllData(model);
    }

    private void observeAllData(RssFragmentViewModel model) {
        model.getNewsDisplayList().observe(this, newsDisplayLists -> {
            if (newsDisplayLists != null) {
                adapter.setItems(newsDisplayLists);
            }
        });
        model.isLoading().observe(this, show -> binding.setShowProgress(show));
    }

    @Override
    public void onLinkClicked(String urlLink) {
        if (getActivity() != null) {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(news.getNewsStationView().getColorPrimary());
            builder.addDefaultShareMenuItem();
            builder.setShowTitle(true);
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(getActivity(), Uri.parse(urlLink));
        }
    }
}
