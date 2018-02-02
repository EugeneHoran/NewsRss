package eugene.com.newsrss.ui.rss;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import eugene.com.newsrss.R;
import eugene.com.newsrss.databinding.FragmentRssBinding;
import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.ui.interfaces.RssLinkCallbacks;
import eugene.com.newsrss.ui.rss.adapters.RssPrimaryRecyclerAdapter;
import eugene.com.newsrss.ui.rss.adapters.RssSecondaryRecyclerAdapter;
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
    private RssPrimaryRecyclerAdapter adapterPrimary;
    private RssSecondaryRecyclerAdapter adapterSecondary;
    private DividerItemDecoration decoration;
    private FragmentRssBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            news = getArguments().getParcelable(ARG_RSS_NEWS);
        }
        if (getActivity() != null) {
            model = ViewModelProviders.of(this, new ViewModelFactory((Application) getActivity().getApplicationContext(), news)).get(RssFragmentViewModel.class);
            decoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        }
        adapterPrimary = new RssPrimaryRecyclerAdapter(this);
        adapterSecondary = new RssSecondaryRecyclerAdapter(
                this,
                !news.getTitle().equalsIgnoreCase("CNBC")
        );
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rss, container, false);
        binding.setTextColor(news.getNewsStationView().getTextColor());
        binding.recyclerPrimary.addItemDecoration(decoration);
        binding.setTitlePrimary(news.getUrlPrimaryTitle());
        binding.setTitleSecondary(news.getUrlSecondaryTitle());
        binding.setHasTopNews(news.getUrlSecondary() != null);
        binding.recyclerPrimary.setFocusable(false);
        binding.recyclerSecondary.setFocusable(false);
        binding.recyclerSecondary.setNestedScrollingEnabled(false);
        binding.recyclerPrimary.setNestedScrollingEnabled(false);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.recyclerSecondary);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerPrimary.setAdapter(adapterPrimary);
        binding.recyclerSecondary.setAdapter(adapterSecondary);
        observePrimaryData(model);
        observeSecondaryData(model);
        binding.nestedScroll.scrollTo(0, 0);
    }

    private void observePrimaryData(RssFragmentViewModel model) {
        model.getPrimaryData().observe(this, response ->
                adapterPrimary.setItemList(
                        response != null && response.data != null ?
                                response.data.getItems() : new ArrayList<>()));
    }

    private void observeSecondaryData(RssFragmentViewModel model) {
        if (model.getSecondaryData() != null) {
            model.getSecondaryData().observe(this, response ->
                    adapterSecondary.setItemList(
                            response != null && response.data != null ?
                                    response.data.getItems() : new ArrayList<>()));
        }
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
