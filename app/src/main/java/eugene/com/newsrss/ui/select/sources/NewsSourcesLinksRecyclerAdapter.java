package eugene.com.newsrss.ui.select.sources;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import eugene.com.newsrss.databinding.RecyclerNewsStationLinksBinding;
import eugene.com.newsrss.db.entities.NewsStationLinks;
import eugene.com.newsrss.ui.common.BaseViewHolder;

public class NewsSourcesLinksRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<NewsStationLinks> linkList = new ArrayList<>();

    public void setLinkList(List<NewsStationLinks> linkList) {
        if (linkList == null) {
            return;
        }
        this.linkList.clear();
        this.linkList.addAll(linkList);
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(RecyclerNewsStationLinksBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(linkList.get(position), null);
    }

    @Override
    public int getItemCount() {
        return linkList.size();
    }
}
