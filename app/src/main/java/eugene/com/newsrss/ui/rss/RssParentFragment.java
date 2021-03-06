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
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import eugene.com.newsrss.R;
import eugene.com.newsrss.databinding.FragmentRssParentBinding;
import eugene.com.newsrss.db.entities.NewsStationView;
import eugene.com.newsrss.ui.interfaces.NewsCallbacks;
import eugene.com.newsrss.ui.rss.adapters.RssPagerAdapter;

public class RssParentFragment extends Fragment implements
        TabLayout.OnTabSelectedListener,
        AppBarLayout.OnOffsetChangedListener {

    private static final String STATE_PAGER_PAGE = "state_pager_page";
    private static final String STATE_APP_BAR_EXPANDED = "state_app_bar_expanded";

    private NewsCallbacks listener;
    private RssParentFragmentViewModel model;
    private FragmentRssParentBinding binding;
    private RssPagerAdapter adapter;
    private RssParentFragmentHelper rssParentHelper;
    private int[] logos;
    private Window window;
    private boolean appBarIsExpanded = true;
    private int pagerPage = 0;
    float swipeRightOffset;

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
        rssParentHelper = new RssParentFragmentHelper();
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
        binding.pager.addOnPageChangeListener(simpleOnPageChangeListener);
        binding.tabs.addOnTabSelectedListener(this);
        binding.appBar.addOnOffsetChangedListener(this);
        observeNewsStations(model);
//        observePagerLogos(model);
//        observeNavigationMenuItems(model);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(STATE_APP_BAR_EXPANDED, appBarIsExpanded);
        outState.putInt(STATE_PAGER_PAGE, binding.pager.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

    private void observeNewsStations(RssParentFragmentViewModel model) {
        model.getNewsStationList().observe(this, newsStationList -> {
            if (newsStationList != null) {
                rssParentHelper.setList(newsStationList);
                adapter.setNewsList(newsStationList);
                binding.pager.setCurrentItem(pagerPage);
                binding.appBar.setExpanded(appBarIsExpanded);
            }
        });
        model.getPagerLogoArray().observe(this, pagerLogos -> {
            if (pagerLogos != null && pagerLogos.length > 0) {
                logos = pagerLogos;
            }
        });
        model.getNavigationMenuList().observe(this, menuItemList -> {
            if (menuItemList != null) {
                binding.tabs.setVisibility(menuItemList.size() == 1 ? View.GONE : View.VISIBLE);
                listener.initNavDrawerMenuItems(menuItemList);
            }
        });
    }

//    private void observePagerLogos(RssParentFragmentViewModel model) {
//        model.getPagerLogoArray().observe(this, pagerLogos -> {
//            if (pagerLogos != null && pagerLogos.length > 0) {
//                logos = pagerLogos;
//            }
//        });
//    }
//
//    private void observeNavigationMenuItems(RssParentFragmentViewModel model) {
//        model.getNavigationMenuList().observe(this, menuItemList -> {
//            if (menuItemList != null) {
//                binding.tabs.setVisibility(menuItemList.size() == 1 ? View.GONE : View.VISIBLE);
//                listener.initNavDrawerMenuItems(menuItemList);
//            }
//        });
//    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        appBarIsExpanded = (verticalOffset == 0);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        changeNewsPage(tab.getPosition(), false);
    }

    private ViewPager.SimpleOnPageChangeListener simpleOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            boolean isSwipeRight = position + positionOffset > swipeRightOffset;
            swipeRightOffset = position + positionOffset;
            boolean scrollPastHalf = positionOffset >= 0.5f;
            listener.onPageSelected(position);
            if (positionOffset == 0) {
                binding.toolbarLogo.setImageResource(logos[position]);
            } else {
                if (scrollPastHalf) {
                    if (isSwipeRight) {
                        binding.toolbarLogo.setImageResource(logos[position + 1]);
                    }
                    binding.toolbarLogo.setAlpha((Math.abs(positionOffset) * 2) - 1.0f);
                } else {
                    if (!isSwipeRight) {
                        binding.toolbarLogo.setImageResource(logos[position]);
                    }
                    binding.toolbarLogo.setAlpha(1.0f - (Math.abs(positionOffset) * 2));
                }
            }
            initColorChange(rssParentHelper.getColorsFormatted(adapter.getCount(), position, positionOffset));
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            binding.appBar.setExpanded(true, true);
        }
    };

    public void changeNewsPage(int position, boolean animate) {
        binding.appBar.setExpanded(true, true);
        binding.pager.setCurrentItem(position, animate);
    }

    private void initColorChange(NewsStationView newsLogoAndColors) {
        if (getActivity() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(newsLogoAndColors.getColorPrimaryDark());
        }
        listener.navIconColor(newsLogoAndColors.getColorAccent());
        binding.appBar.setBackgroundColor(newsLogoAndColors.getColorPrimary());
        binding.tabs.setSelectedTabIndicatorColor(newsLogoAndColors.getColorAccent());
        binding.tabs.setTabTextColors(ColorUtils.setAlphaComponent(newsLogoAndColors.getColorAccent(), 180), newsLogoAndColors.getColorAccent());
    }

    public View getSharedView() {
        return binding.appBar;
    }

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

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * Not Used
     */

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }
}

