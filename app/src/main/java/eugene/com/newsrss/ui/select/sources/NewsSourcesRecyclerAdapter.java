package eugene.com.newsrss.ui.select.sources;


import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import eugene.com.newsrss.databinding.RecyclerNewsStationPrimaryBinding;
import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.ui.common.BaseViewHolder;
import eugene.com.newsrss.ui.interfaces.StationsListCallbacks;
import eugene.com.newsrss.util.StationsDiffUtil;

public class NewsSourcesRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private StationsListCallbacks listener;
    private List<NewsStation> newsStationList = new ArrayList<>();

    NewsSourcesRecyclerAdapter(StationsListCallbacks listener) {
        this.listener = listener;
    }

    void setNewsStationList(List<NewsStation> newNewsStationList) {
        if (newsStationList == null) {
            return;
        }
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new StationsDiffUtil(newsStationList, newNewsStationList));
        this.newsStationList.clear();
        this.newsStationList.addAll(newNewsStationList);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(RecyclerNewsStationPrimaryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(newsStationList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return newsStationList.size();
    }
}
