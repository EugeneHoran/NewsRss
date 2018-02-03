package eugene.com.newsrss.ui.common;

import android.support.v4.app.FragmentManager;

import eugene.com.newsrss.R;
import eugene.com.newsrss.ui.interfaces.NavControllerCallbacks;
import eugene.com.newsrss.ui.rss.RssParentFragment;
import eugene.com.newsrss.ui.select.sources.NewsSourcesFragment;

public class NavigationController {
    private static final String TAG_FRAG_SELECTOR = "tag_frag_select_news_sources";
    private static final String TAG_FRAG_RSS_FEED = "tag_frag_rss_feed";
    private final FragmentManager fm;
    private final NavControllerCallbacks listener;
    private final int container;

    private NewsSourcesFragment fragmentSelectNewsSource;
    private RssParentFragment fragmentRss;

    public NavigationController(FragmentManager fm, NavControllerCallbacks listener) {
        this.fm = fm;
        this.listener = listener;
        this.container = R.id.container;
    }

    public void initStart(boolean newsInitiated) {
        if (newsInitiated) {
            navToRss();
        } else {
            navToSelectNewsSource();
        }
    }

    public void navToSelectNewsSource() {
        fragmentSelectNewsSource = NewsSourcesFragment.newInstance();
        fm.beginTransaction().replace(container, fragmentSelectNewsSource, TAG_FRAG_SELECTOR).commit();
        listener.lockDrawer(true);
    }

    public void navToRss() {
        fragmentRss = RssParentFragment.newInstance();
        fm.beginTransaction().replace(container, fragmentRss, TAG_FRAG_RSS_FEED).commit();
        listener.lockDrawer(false);
    }

    public RssParentFragment getRssFragment() {
        if (fragmentRss != null) {
            return fragmentRss;
        } else if (fm.findFragmentByTag(TAG_FRAG_RSS_FEED) != null) {
            return (RssParentFragment) fm.findFragmentByTag(TAG_FRAG_RSS_FEED);
        }
        return null;
    }

    public NewsSourcesFragment getSelectNewsSourceFragment() {
        return fragmentSelectNewsSource;
    }
}
