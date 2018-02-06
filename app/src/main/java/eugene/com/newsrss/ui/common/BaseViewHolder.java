package eugene.com.newsrss.ui.common;

import android.databinding.ViewDataBinding;
import android.text.TextUtils;

import eugene.com.newsrss.databinding.RecyclerNewsStationLinksBinding;
import eugene.com.newsrss.databinding.RecyclerPrimaryItemBinding;
import eugene.com.newsrss.databinding.RecyclerSecondaryItemBinding;
import eugene.com.newsrss.databinding.RecyclerSecondaryItemNoImageBinding;
import eugene.com.newsrss.db.entities.NewsStationLinks;
import eugene.com.newsrss.model.Item;
import eugene.com.newsrss.ui.interfaces.RssLinkCallbacks;

public class BaseViewHolder extends BaseImpViewHolder {
    private ViewDataBinding binding;

    public BaseViewHolder(ViewDataBinding binding) {
        super(binding);
        this.binding = binding;
    }

    @Override
    public void bindView(Object object, BaseInterface listener) {
        // Primary
        if (object instanceof Item) {
            Item item = (Item) object;
            if (binding instanceof RecyclerPrimaryItemBinding) {
                RecyclerPrimaryItemBinding binder = (RecyclerPrimaryItemBinding) binding;
                binder.setItem(item);
                binder.setListener((RssLinkCallbacks) listener);
            } else if (binding instanceof RecyclerSecondaryItemBinding) {
                RecyclerSecondaryItemBinding binder = (RecyclerSecondaryItemBinding) binding;
                if (TextUtils.isEmpty(item.getDescription())) {
                    binder.title.setMaxLines(4);
                }
                binder.setItem(item);
                binder.setListener((RssLinkCallbacks) listener);
            } else if (binding instanceof RecyclerSecondaryItemNoImageBinding) {
                RecyclerSecondaryItemNoImageBinding binder = (RecyclerSecondaryItemNoImageBinding) binding;
                binder.setItem(item);
                binder.setListener((RssLinkCallbacks) listener);
            }
        }
    }
}
