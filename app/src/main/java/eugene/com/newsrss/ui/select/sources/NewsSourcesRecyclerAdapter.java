package eugene.com.newsrss.ui.select.sources;


import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import eugene.com.newsrss.R;
import eugene.com.newsrss.databinding.RecyclerNewsStationPrimaryBinding;
import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.ui.interfaces.StationLinkChangeCallback;
import eugene.com.newsrss.ui.interfaces.StationsListCallbacks;
import eugene.com.newsrss.util.MyTextUtils;
import eugene.com.newsrss.util.StationsDiffUtil;

public class NewsSourcesRecyclerAdapter extends RecyclerView.Adapter<NewsSourcesRecyclerAdapter.NewsSourcesViewHolder> {
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
    public NewsSourcesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsSourcesViewHolder(RecyclerNewsStationPrimaryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(NewsSourcesViewHolder holder, int position) {
        holder.bindView(newsStationList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsStationList.size();
    }

    class NewsSourcesViewHolder extends RecyclerView.ViewHolder implements StationLinkChangeCallback {
        RecyclerNewsStationPrimaryBinding binder;
        private NewsSourcesLinksRecyclerAdapter adapter;
        private NewsStation newsStation;

        NewsSourcesViewHolder(RecyclerNewsStationPrimaryBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
            adapter = new NewsSourcesLinksRecyclerAdapter(this);
        }

        void bindView(NewsStation newsStation) {
            this.newsStation = newsStation;
            adapter.setNewsStation(newsStation);
            binder.recycler.setNestedScrollingEnabled(false);
            binder.recycler.setAdapter(adapter);
            binder.setListener(listener);
            binder.setStation(newsStation);
            int margin = (int) MyTextUtils.convertDpToPixel(newsStation.isShow() ? 8 : 0);
            RecyclerView.LayoutParams cardParam = (RecyclerView.LayoutParams) binder.card.getLayoutParams();
            cardParam.setMargins(
                    margin,
                    margin,
                    margin,
                    margin
            );
            binder.card.setCardElevation(newsStation.isShow() ? 4 : 0);
            binder.card.setCardBackgroundColor(newsStation.isShow() ?
                    ContextCompat.getColor(binder.card.getContext(), android.R.color.white) :
                    ContextCompat.getColor(binder.card.getContext(), R.color.colorBackground));
            binder.checkbox.setOnCheckedChangeListener((view, isChecked) -> {
                if (newsStation.isShow() != isChecked) {
                    newsStation.setPosition(getAdapterPosition());
                    listener.stationSelected(newsStation);
                }
            });
        }

        @Override
        public void onStationCheckChange(int position) {
            listener.stationSelected(new NewsStation(newsStation, position));
        }
    }
}
