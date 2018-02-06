package eugene.com.newsrss.ui.select.sources;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import eugene.com.newsrss.databinding.RecyclerNewsStationLinksBinding;
import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.db.entities.NewsStationLinks;
import eugene.com.newsrss.ui.interfaces.StationLinkChangeCallback;

public class NewsSourcesLinksRecyclerAdapter extends RecyclerView.Adapter<NewsSourcesLinksRecyclerAdapter.StationViewHolder> {
    private List<NewsStationLinks> linkList = new ArrayList<>();
    private StationLinkChangeCallback listener;

    public NewsSourcesLinksRecyclerAdapter(StationLinkChangeCallback listener) {
        this.listener = listener;
    }

    public void setNewsStation(NewsStation newsStation) {
        if (newsStation == null || newsStation.getNewsStationLinks() == null) {
            return;
        }
        this.linkList.clear();
        this.linkList.addAll(newsStation.getNewsStationLinks());
        notifyDataSetChanged();
    }

    @Override
    public StationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StationViewHolder(RecyclerNewsStationLinksBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(StationViewHolder holder, int position) {
        holder.bindView(linkList.get(position));
    }

    @Override
    public int getItemCount() {
        return linkList.size();
    }

    class StationViewHolder extends RecyclerView.ViewHolder {
        RecyclerNewsStationLinksBinding binder;

        StationViewHolder(RecyclerNewsStationLinksBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }

        void bindView(NewsStationLinks newsStationLink) {
            binder.setLink(newsStationLink);
            binder.checkbox.setOnCheckedChangeListener((view, isChecked) -> {
                if (newsStationLink.isChecked() != isChecked) {
                    listener.onStationCheckChange(getAdapterPosition());
                }
            });
        }
    }
}
