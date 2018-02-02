package eugene.com.newsrss.util;

import android.support.v7.util.DiffUtil;
import android.util.Log;

import java.util.List;

import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.db.entities.NewsStationLinks;


public class StationsDiffUtil extends DiffUtil.Callback {
    private List<NewsStation> oldList, newList;

    public StationsDiffUtil(List<NewsStation> oldList, List<NewsStation> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        NewsStation oldItem = oldList.get(oldItemPosition);
        NewsStation newItem = newList.get(newItemPosition);
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        NewsStation oldItem = oldList.get(oldItemPosition);
        NewsStation newItem = newList.get(newItemPosition);
        if (oldItem.isShow() == newItem.isShow()) {
            for (int i = 0; i < oldItem.getNewsStationLinks().size(); i++) {
                NewsStationLinks oldLink = oldItem.getNewsStationLinks().get(i);
                NewsStationLinks newLink = newItem.getNewsStationLinks().get(i);
                if (oldLink.isChecked() != newLink.isChecked()) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
