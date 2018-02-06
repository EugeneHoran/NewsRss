package eugene.com.newsrss.ui.rss.adapters;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import eugene.com.newsrss.databinding.RecyclerFeedHorizontalBinding;
import eugene.com.newsrss.databinding.RecyclerFeedVerticalBinding;
import eugene.com.newsrss.model.NewsDisplayList;
import eugene.com.newsrss.ui.interfaces.RssLinkCallbacks;

public class RssFeedRecyclerAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_VERTICAL = 0;
    private static final int VIEW_TYPE_HORIZONTAL = 1;

    private List<NewsDisplayList> itemList = new ArrayList<>();

    private RssLinkCallbacks listener;
    private boolean hasImages;

    public RssFeedRecyclerAdapter(RssLinkCallbacks listener, String title) {
        this.listener = listener;
        this.hasImages = !title.equalsIgnoreCase("CNBC");
    }

    public void setItems(List<NewsDisplayList> newsDisplayLists) {
        itemList.clear();
        itemList.addAll(newsDisplayLists);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (itemList.get(position).isPrimary()) {
            return VIEW_TYPE_VERTICAL;
        } else {
            return VIEW_TYPE_HORIZONTAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_VERTICAL) {
            return new VerticalViewHolder(RecyclerFeedVerticalBinding.inflate(layoutInflater, parent, false));
        } else if (viewType == VIEW_TYPE_HORIZONTAL) {
            return new HorizontalViewHolder(RecyclerFeedHorizontalBinding.inflate(layoutInflater, parent, false));
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsDisplayList newsDisplayList = itemList.get(position);
        switch (getItemViewType(position)) {
            case VIEW_TYPE_VERTICAL:
                ((VerticalViewHolder) holder).bindView(newsDisplayList);
                break;
            case VIEW_TYPE_HORIZONTAL:
                ((HorizontalViewHolder) holder).bindView(newsDisplayList);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class VerticalViewHolder extends RecyclerView.ViewHolder {
        private RssFeedVerticalRecyclerAdapter adapter;
        private RecyclerFeedVerticalBinding binding;

        VerticalViewHolder(RecyclerFeedVerticalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            adapter = new RssFeedVerticalRecyclerAdapter(listener);
            binding.recycler.setNestedScrollingEnabled(false);
            binding.recycler.addItemDecoration(new DividerItemDecoration(binding.recycler.getContext(), DividerItemDecoration.VERTICAL));
            binding.recycler.setAdapter(adapter);
        }

        void bindView(NewsDisplayList newsDisplayList) {
            binding.setTitle(newsDisplayList.getTitle());
            binding.setTextColor(newsDisplayList.getTextColor());
            adapter.setItemList(newsDisplayList.getArticles());
        }
    }

    class HorizontalViewHolder extends RecyclerView.ViewHolder {
        private RssFeedHorizontalRecyclerAdapter adapter;
        private RecyclerFeedHorizontalBinding binding;

        HorizontalViewHolder(RecyclerFeedHorizontalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            adapter = new RssFeedHorizontalRecyclerAdapter(listener, hasImages);
            SnapHelper snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView(binding.recycler);
            binding.recycler.setNestedScrollingEnabled(false);
            binding.recycler.setAdapter(adapter);
        }

        void bindView(NewsDisplayList newsDisplayList) {
            binding.setTitle(newsDisplayList.getTitle());
            binding.setTextColor(newsDisplayList.getTextColor());
            adapter.setItemList(newsDisplayList.getArticles());
        }
    }
}
