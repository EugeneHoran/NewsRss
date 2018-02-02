package eugene.com.newsrss.ui.rss.adapters;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.ui.rss.RssFragment;

public class RssPagerAdapter extends FragmentPagerAdapter {
    private List<NewsStation> newsStationList;
    private Fragment[] fragments;

    public RssPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setNewsList(List<NewsStation> newsStationList) {
        this.newsStationList = newsStationList;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments != null && fragments.length > position && fragments[position] != null) {
            return fragments[position];
        }
        if (fragments == null) {
            fragments = new Fragment[getCount()];
        }
        fragments[position] = RssFragment.newInstance(newsStationList.get(position));
        return fragments[position];
    }

    @Override
    public int getCount() {
        return newsStationList != null ? newsStationList.size() : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return newsStationList.get(position).getTitle();
    }
}
