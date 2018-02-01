package eugene.com.newsrss.ui.rss;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import eugene.com.newsrss.databinding.RecyclerPrimaryItemBinding;
import eugene.com.newsrss.model.Item;
import eugene.com.newsrss.ui.interfaces.RssLinkCallbacks;

public class RssPrimaryRecyclerAdapter extends RecyclerView.Adapter<BaseRssViewHolder> {
    private List<Item> itemList = new ArrayList<>();
    private RssLinkCallbacks listener;

    RssPrimaryRecyclerAdapter(RssLinkCallbacks listener) {
        this.listener = listener;
    }

    void setItemList(List<Item> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    @Override
    public BaseRssViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRssViewHolder(RecyclerPrimaryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(BaseRssViewHolder holder, int position) {
        holder.bindView(itemList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
