package eugene.com.newsrss.ui.common;

import android.support.v4.app.FragmentManager;

import eugene.com.newsrss.R;
import eugene.com.newsrss.ui.rss.RssParentFragment;

public class NavigationController {
    private static final String TAG_FRAG_RSS_FEED = "tag_frag_rss_feed";
    private final FragmentManager fm;
    private final int container;

    private RssParentFragment fragmentRss;

    public NavigationController(FragmentManager fm) {
        this.fm = fm;
        this.container = R.id.container;
    }

    public void navToRss() {
        fragmentRss = RssParentFragment.newInstance();
        fm.beginTransaction().replace(container, fragmentRss, TAG_FRAG_RSS_FEED).commit();
    }

    public RssParentFragment getRssFragment() {
        if (fragmentRss != null) {
            return fragmentRss;
        } else if (fm.findFragmentByTag(TAG_FRAG_RSS_FEED) != null) {
            return (RssParentFragment) fm.findFragmentByTag(TAG_FRAG_RSS_FEED);
        }
        return null;
    }
}
