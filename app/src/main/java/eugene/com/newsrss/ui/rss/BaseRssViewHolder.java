package eugene.com.newsrss.ui.rss;

import android.databinding.ViewDataBinding;
import android.text.TextUtils;

import eugene.com.newsrss.databinding.RecyclerPrimaryItemBinding;
import eugene.com.newsrss.databinding.RecyclerSecondaryItemBinding;
import eugene.com.newsrss.databinding.RecyclerSecondaryItemNoImageBinding;
import eugene.com.newsrss.model.Item;
import eugene.com.newsrss.ui.common.BaseImpViewHolder;
import eugene.com.newsrss.ui.interfaces.RssLinkCallbacks;

class BaseRssViewHolder extends BaseImpViewHolder {
    private ViewDataBinding binding;

    BaseRssViewHolder(ViewDataBinding binding) {
        super(binding);
        this.binding = binding;
    }

    @Override
    public void bindView(Item item, RssLinkCallbacks listener) {
        if (binding instanceof RecyclerPrimaryItemBinding) {
            RecyclerPrimaryItemBinding binder = (RecyclerPrimaryItemBinding) binding;
            binder.setItem(item);
            binder.setListener(listener);
        } else if (binding instanceof RecyclerSecondaryItemBinding) {
            RecyclerSecondaryItemBinding binder = (RecyclerSecondaryItemBinding) binding;
            if (TextUtils.isEmpty(item.getDescription())) {
                binder.title.setMaxLines(4);
            }
            binder.setItem(item);
            binder.setListener(listener);
        } else if (binding instanceof RecyclerSecondaryItemNoImageBinding) {
            RecyclerSecondaryItemNoImageBinding binder = (RecyclerSecondaryItemNoImageBinding) binding;
            binder.setItem(item);
            binder.setListener(listener);
        }
    }
}
