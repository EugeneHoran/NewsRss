package eugene.com.newsrss.ui.rss;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import eugene.com.newsrss.databinding.RecyclerSecondaryItemBinding;
import eugene.com.newsrss.databinding.RecyclerSecondaryItemNoImageBinding;
import eugene.com.newsrss.model.Item;
import eugene.com.newsrss.ui.interfaces.RssLinkCallbacks;

public class RssSecondaryRecyclerAdapter extends RecyclerView.Adapter<BaseRssViewHolder> {
    private List<Item> itemList = new ArrayList<>();
    private RssLinkCallbacks listener;
    private boolean showImage = true;

    RssSecondaryRecyclerAdapter(RssLinkCallbacks listener, boolean showImage) {
        this.listener = listener;
        this.showImage = showImage;
    }

    void setItemList(List<Item> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    @Override
    public BaseRssViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (showImage) {
            return new BaseRssViewHolder(RecyclerSecondaryItemBinding.inflate(layoutInflater, parent, false));
        } else {
            return new BaseRssViewHolder(RecyclerSecondaryItemNoImageBinding.inflate(layoutInflater, parent, false));
        }
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
