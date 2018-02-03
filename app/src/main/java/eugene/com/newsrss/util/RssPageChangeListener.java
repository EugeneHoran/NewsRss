package eugene.com.newsrss.util;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v4.view.ViewPager;

import java.util.List;

import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.ui.interfaces.RssPageChangeCallbacks;
import eugene.com.newsrss.ui.rss.RssParentFragmentHelper;

public class RssPageChangeListener implements LifecycleObserver {
    private float swipeRightOffset;

    private ViewPager pager;
    private RssPageChangeCallbacks listener;
    private RssParentFragmentHelper rssParentHelper;

    public RssPageChangeListener(ViewPager pager, RssPageChangeCallbacks listener) {
        rssParentHelper = new RssParentFragmentHelper();
        this.pager = pager;
        this.listener = listener;
        this.pager.addOnPageChangeListener(pageChangeListener);
    }

    public void setNewsStationList(List<NewsStation> newsStationList) {
        rssParentHelper.setList(newsStationList);
    }

    private ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            boolean isSwipeRight = position + positionOffset > swipeRightOffset;
            swipeRightOffset = position + positionOffset;
            boolean scrollPastHalf = positionOffset >= 0.5f;

//            // set nav item selected
            listener.onPageSelected(position);

            if (positionOffset == 0) {
                listener.setToolbarLogo(position);
            } else {
                if (scrollPastHalf) {
                    if (isSwipeRight) {
                        listener.setToolbarLogo(position + 1);
                    }
                    listener.setToolbarLogoAlpha((Math.abs(positionOffset) * 2) - 1.0f);
                } else {
                    if (!isSwipeRight) {
                        listener.setToolbarLogo(position);
                    }
                    listener.setToolbarLogoAlpha(1.0f - (Math.abs(positionOffset) * 2));
                }
            }
            if (pager.getAdapter() != null) {
                listener.onPageColorChange(rssParentHelper.getColorsFormatted(pager.getAdapter().getCount(), position, positionOffset));
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            listener.expandAppBar();
        }
    };


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        listener = null;
        pager.removeOnPageChangeListener(pageChangeListener);
        pager = null;
    }
}
