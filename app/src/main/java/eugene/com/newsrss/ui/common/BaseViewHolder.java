package eugene.com.newsrss.ui.common;

import android.databinding.ViewDataBinding;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import eugene.com.newsrss.R;
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
import eugene.com.newsrss.util.MyTextUtils;

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
}
