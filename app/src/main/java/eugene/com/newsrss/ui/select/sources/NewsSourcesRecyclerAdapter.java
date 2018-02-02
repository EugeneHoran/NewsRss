package eugene.com.newsrss.ui.select.sources;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import eugene.com.newsrss.databinding.RecyclerNewsStationPrimaryBinding;
import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.ui.common.BaseViewHolder;
import eugene.com.newsrss.ui.interfaces.StationsListCallbacks;

public class NewsSourcesRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private StationsListCallbacks listener;
    private List<NewsStation> newsStationList = new ArrayList<>();

    NewsSourcesRecyclerAdapter(StationsListCallbacks listener) {
        this.listener = listener;
    }

    void setNewsStationList(List<NewsStation> newsStationList) {
        if (newsStationList == null) {
            return;
        }
        this.newsStationList.clear();
        this.newsStationList.addAll(newsStationList);
        notifyDataSetChanged();
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
