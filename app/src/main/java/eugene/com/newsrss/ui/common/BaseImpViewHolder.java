package eugene.com.newsrss.ui.common;


import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import eugene.com.newsrss.model.Item;
import eugene.com.newsrss.ui.interfaces.RssLinkCallbacks;

public abstract class BaseImpViewHolder extends RecyclerView.ViewHolder {
    public BaseImpViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
    }

    public abstract void bindView(Item item, RssLinkCallbacks listener);
}
