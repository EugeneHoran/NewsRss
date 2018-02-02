package eugene.com.newsrss.ui.select.sources;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import eugene.com.newsrss.R;
import eugene.com.newsrss.databinding.FragmentSelectNewsSourcesBinding;
import eugene.com.newsrss.db.entities.NewsStation;
import eugene.com.newsrss.ui.interfaces.StationsListCallbacks;

public class NewsSourcesFragment extends Fragment implements StationsListCallbacks {
    private NewsSourcesFragmentViewModel model;
    private FragmentSelectNewsSourcesBinding binding;
    private NewsSourcesRecyclerAdapter adapter;

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
        if (getActivity() != null)
            binding.recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recycler.setAdapter(adapter);
        observeNewsSources(model);
    }

    private void observeNewsSources(NewsSourcesFragmentViewModel model) {
        model.getNewsStationList().observe(this, newsStationList -> {
            adapter.setNewsStationList(newsStationList);
        });
    }

    @Override
    public void stationSelected(NewsStation newsStation) {
        Toast.makeText(getActivity(), newsStation.getTitle(), Toast.LENGTH_SHORT).show();
    }

    public NewsSourcesFragment() {
    }

    public static NewsSourcesFragment newInstance() {
        return new NewsSourcesFragment();
    }
}
