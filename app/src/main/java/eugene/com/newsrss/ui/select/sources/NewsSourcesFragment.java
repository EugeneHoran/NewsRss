package eugene.com.newsrss.ui.select.sources;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import eugene.com.newsrss.R;
import eugene.com.newsrss.databinding.FragmentSelectNewsSourcesBinding;
import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.ui.interfaces.NewsCallbacks;
import eugene.com.newsrss.ui.interfaces.SelectorCallbacks;
import eugene.com.newsrss.ui.interfaces.StationsListCallbacks;

public class NewsSourcesFragment extends Fragment implements StationsListCallbacks {
    private NewsSourcesFragmentViewModel model;
    private FragmentSelectNewsSourcesBinding binding;
    private NewsSourcesRecyclerAdapter adapter;
    private SelectorCallbacks listener;
    private Integer scrollPos;
    private boolean enableBtn = false;
    private int colorDisabled;
    private int colorEnabled;

    public NewsSourcesFragment() {
    }

    public static NewsSourcesFragment newInstance() {
        return new NewsSourcesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(this).get(NewsSourcesFragmentViewModel.class);
        adapter = new NewsSourcesRecyclerAdapter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_news_sources, container, false);
        if (getActivity() != null) {
            colorDisabled = Color.parseColor("#9E9E9E");
            colorEnabled = ContextCompat.getColor(getActivity(), R.color.colorAccent);
            binding.recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recycler.setAdapter(adapter);
        binding.btnNext.setOnClickListener(v -> {
            if (enableBtn) {
                listener.navToRss();
            }
        });
        observeNewsSources(model);
    }

    @Override
    public void stationSelected(NewsStation newsStation) {
        scrollPos = newsStation.getPosition();
        model.updateStation(newsStation);
    }

    private void observeNewsSources(NewsSourcesFragmentViewModel model) {
        model.getNewsStationList().observe(this, newsStationList -> {
            adapter.setNewsStationList(newsStationList);
            initButton(newsStationList);
            if (scrollPos != null) {
                binding.recycler.getLayoutManager().scrollToPosition(scrollPos);
                scrollPos = null;
            }
        });
    }

    private void initButton(List<NewsStation> newsStationList) {
        if (newsStationList != null) {
            int totalSelected = 0;
            for (NewsStation newsStation : newsStationList) {
                if (newsStation.isShow()) {
                    totalSelected++;
                }
            }
            enableBtn = totalSelected != 0;
            binding.btnNext.setBackgroundColor(totalSelected == 0 ? colorDisabled : colorEnabled);
            return;
        }
        enableBtn = false;
        binding.btnNext.setBackgroundColor(colorDisabled);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SelectorCallbacks) {
            listener = (SelectorCallbacks) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentCallbacks");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}
