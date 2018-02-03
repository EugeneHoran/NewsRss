package eugene.com.newsrss.ui.common;

import android.os.Build;
import android.support.transition.AutoTransition;
import android.support.transition.Explode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import eugene.com.newsrss.R;
import eugene.com.newsrss.ui.interfaces.NavControllerCallbacks;
import eugene.com.newsrss.ui.rss.RssParentFragment;
import eugene.com.newsrss.ui.select.sources.NewsSourcesFragment;
import eugene.com.newsrss.util.AnimationTransition;

public class NavigationController {
    private static final String TAG_FRAG_SELECTOR = "tag_frag_select_news_sources";
    private static final String TAG_FRAG_RSS_FEED = "tag_frag_rss_feed";
    private final FragmentManager fm;
    private final NavControllerCallbacks listener;
    private final int container;

    private RssParentFragment fragmentRss;

    public NavigationController(FragmentManager fm, NavControllerCallbacks listener) {
        this.fm = fm;
        this.listener = listener;
        this.container = R.id.container;
    }

    public void initStart(boolean newsInitiated) {
        if (newsInitiated) {
            navToRss(null);
        } else {
            navToSelectNewsSource(null);
        }
    }

    public void navToSelectNewsSource(View view) {
        NewsSourcesFragment fragmentSelectNewsSource = NewsSourcesFragment.newInstance();
        initTransitionN(fragmentSelectNewsSource);
        FragmentTransaction transaction = fm.beginTransaction();
        if (view != null) {
            transaction.addSharedElement(view, "app_bar");
        }
        transaction.replace(container, fragmentSelectNewsSource, TAG_FRAG_SELECTOR).commit();
        listener.lockDrawer(true);
    }

    public void navToRss(View view) {
        fragmentRss = RssParentFragment.newInstance();
        initTransitionN(fragmentRss);
        FragmentTransaction transaction = fm.beginTransaction();
        if (view != null) {
            transaction.addSharedElement(view, "app_bar");
        }
        transaction.replace(container, fragmentRss, TAG_FRAG_RSS_FEED).commit();
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

    private void initTransitionN(Fragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementReturnTransition(new AutoTransition());
            fragment.setSharedElementEnterTransition(new AutoTransition());
        }
    }
}
