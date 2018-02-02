package eugene.com.newsrss.ui.common;

import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import eugene.com.newsrss.databinding.RecyclerNewsStationLinksBinding;
import eugene.com.newsrss.databinding.RecyclerNewsStationPrimaryBinding;
import eugene.com.newsrss.databinding.RecyclerPrimaryItemBinding;
import eugene.com.newsrss.databinding.RecyclerSecondaryItemBinding;
import eugene.com.newsrss.databinding.RecyclerSecondaryItemNoImageBinding;
import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.db.entities.NewsStationLinks;
import eugene.com.newsrss.model.Item;
import eugene.com.newsrss.ui.interfaces.RssLinkCallbacks;
import eugene.com.newsrss.ui.interfaces.StationsListCallbacks;
import eugene.com.newsrss.ui.select.sources.NewsSourcesLinksRecyclerAdapter;

public class BaseViewHolder extends BaseImpViewHolder {
    private ViewDataBinding binding;
    private NewsSourcesLinksRecyclerAdapter adapter;

    public BaseViewHolder(ViewDataBinding binding) {
        super(binding);
        this.binding = binding;
        if (binding instanceof RecyclerNewsStationPrimaryBinding) {
            adapter = new NewsSourcesLinksRecyclerAdapter();
        }
    }

    @Override
    public void bindView(Object object, BaseInterface listener) {
        if (object instanceof NewsStation && binding instanceof RecyclerNewsStationPrimaryBinding) {
            bindNewsSources(
                    (RecyclerNewsStationPrimaryBinding) binding,
                    (NewsStation) object,
                    (StationsListCallbacks) listener
            );
        } else if (object instanceof NewsStationLinks) {
            if (binding instanceof RecyclerNewsStationLinksBinding) {
                RecyclerNewsStationLinksBinding binder = (RecyclerNewsStationLinksBinding) binding;
                binder.setLink((NewsStationLinks) object);
            }
        } else if (object instanceof Item) {
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

    private void bindNewsSources(RecyclerNewsStationPrimaryBinding binder, NewsStation newsStation, StationsListCallbacks listener) {
        adapter.setLinkList(newsStation.getNewsStationLinks());
        binder.recycler.setNestedScrollingEnabled(false);
        binder.recycler.setAdapter(adapter);
        binder.setListener(listener);
        binder.setStation(newsStation);
        binder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) ->
                binder.recycler.setVisibility(isChecked ? View.VISIBLE : View.GONE));
    }
}
