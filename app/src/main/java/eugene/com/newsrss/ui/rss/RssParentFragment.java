package eugene.com.newsrss.ui.rss;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.ColorUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.List;

import eugene.com.newsrss.R;
import eugene.com.newsrss.databinding.FragmentRssParentBinding;
import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.db.entities.NewsStationView;
import eugene.com.newsrss.ui.interfaces.NewsCallbacks;
import eugene.com.newsrss.ui.interfaces.RssPageChangeCallbacks;
import eugene.com.newsrss.ui.rss.adapters.RssPagerAdapter;
import eugene.com.newsrss.util.RssPageChangeListener;
import eugene.com.newsrss.util.SimpleOnTabSelectedListener;

public class RssParentFragment extends Fragment implements
        AppBarLayout.OnOffsetChangedListener {

    private static final String STATE_PAGER_PAGE = "state_pager_page";
    private static final String STATE_APP_BAR_EXPANDED = "state_app_bar_expanded";

    private NewsCallbacks listener;
    private RssParentFragmentViewModel model;
    private FragmentRssParentBinding binding;
    private RssPageChangeListener rssPageChangeListener;
    private RssPagerAdapter adapter;
    private int[] logos;
    private Window window;
    private boolean appBarIsExpanded = true;
    private int pagerPage = 0;

    public RssParentFragment() {
    }

    public static RssParentFragment newInstance() {
        return new RssParentFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = getActivity().getWindow();
        }
        model = ViewModelProviders.of(this).get(RssParentFragmentViewModel.class);
        adapter = new RssPagerAdapter(getChildFragmentManager());
        if (savedInstanceState != null) {
            pagerPage = savedInstanceState.getInt(STATE_PAGER_PAGE, 0);
            appBarIsExpanded = savedInstanceState.getBoolean(STATE_APP_BAR_EXPANDED, true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rss_parent, container, false);
        listener.initActionbar(binding.toolbar);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.pager.setAdapter(adapter);
        binding.tabs.setupWithViewPager(binding.pager);
        rssPageChangeListener = new RssPageChangeListener(binding.pager, rssPageChangeCallbacks);
        getLifecycle().addObserver(rssPageChangeListener);
        binding.tabs.addOnTabSelectedListener(simpleOnTabSelectedListener);
        binding.appBar.addOnOffsetChangedListener(this);
        observeDataChanges(model);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(STATE_APP_BAR_EXPANDED, appBarIsExpanded);
        outState.putInt(STATE_PAGER_PAGE, binding.pager.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

    /**
     * Observe data changes
     *
     * @param model RssParentFragmentViewModel.class
     */
    private void observeDataChanges(RssParentFragmentViewModel model) {
        // station list
        model.getNewsStationList().observe(this, this::initNewsStationList);
        // station logos
        model.getPagerLogos().observe(this, pagerLogos -> logos = pagerLogos);
        // nav menu logo list
        model.getNavigationMenuItem().observe(this, menuItemList ->
                listener.initNavDrawerMenuItems(menuItemList));
    }

    /**
     * Init views based on List<NewsStation> data changes
     *
     * @param newsStationList list of news stations
     */
    private void initNewsStationList(@NonNull List<NewsStation> newsStationList) {
        binding.tabs.setVisibility(newsStationList.size() == 1 ? View.GONE : View.VISIBLE);
        rssPageChangeListener.setNewsStationList(newsStationList);
        adapter.setNewsList(newsStationList);
        binding.pager.setCurrentItem(pagerPage);
        binding.appBar.setExpanded(appBarIsExpanded);
    }

    /**
     * Handle View changes on pager adapter changes
     */
    private RssPageChangeCallbacks rssPageChangeCallbacks = new RssPageChangeCallbacks() {
        @Override
        public void setToolbarLogo(int position) {
            binding.toolbarLogo.setImageResource(logos[position]);
        }

        @Override
        public void setToolbarLogoAlpha(float alpha) {
            binding.toolbarLogo.setAlpha(alpha);
        }

        @Override
        public void onPageSelected(int position) {
            listener.onPageSelected(position);
        }

        @Override
        public void onPageColorChange(NewsStationView newsStationView) {
            if (newsStationView == null) {
                return;
            }
            if (getActivity() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(newsStationView.getColorPrimaryDark());
            }
            listener.navIconColor(newsStationView.getColorAccent());
            binding.appBar.setBackgroundColor(newsStationView.getColorPrimary());
            binding.tabs.setSelectedTabIndicatorColor(newsStationView.getColorAccent());
            binding.tabs.setTabTextColors(ColorUtils.setAlphaComponent(newsStationView.getColorAccent(), 180), newsStationView.getColorAccent());
        }

        @Override
        public void expandAppBar() {
            binding.appBar.setExpanded(true, true);
        }
    };
    /**
     * Tab selected listener
     * Change pager page without animation
     */
    private SimpleOnTabSelectedListener simpleOnTabSelectedListener = new SimpleOnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            changeNewsPage(tab.getPosition(), false);
        }
    };

    /**
     * Save state of appBar expanded
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        appBarIsExpanded = (verticalOffset == 0);
    }

    /**
     * Change pager page
     *
     * @param position pager pos
     * @param animate  animate page change
     */
    public void changeNewsPage(int position, boolean animate) {
        binding.appBar.setExpanded(true, true);
        binding.pager.setCurrentItem(position, animate);
    }

    /**
     * Init interface callbacks
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewsCallbacks) {
            listener = (NewsCallbacks) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentCallbacks");
        }
    }

    /**
     * Remove interface callbacks
     */
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}

