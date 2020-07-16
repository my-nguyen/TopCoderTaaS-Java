package com.nguyen.topcodertaasi.ui.region_stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nguyen.topcodertaasi.R;
import com.nguyen.topcodertaasi.Utils;
import com.nguyen.topcodertaasi.databinding.FragmentRegionStatsBinding;
import com.nguyen.topcodertaasi.model.CountriesAdapter;
import com.nguyen.topcodertaasi.model.Countries;
import com.nguyen.topcodertaasi.model.EndlessScrollListener;

public class RegionStatsFragment extends Fragment {
    private RegionStatsViewModel regionStatsViewModel;
    private FragmentRegionStatsBinding binding;
    private CountriesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        regionStatsViewModel = new ViewModelProvider(this).get(RegionStatsViewModel.class);
        binding = FragmentRegionStatsBinding.inflate(inflater, container, false);
        adapter = CountriesAdapter.getInstance();
        binding.recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.addOnScrollListener(new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                fetchPage();
            }
        });
        // if switching to this screen (Region Stats) for the first time, fetch first page and display it
        // otherwise, the fetch has been done, so do nothing and the pages will be displayed
        /*if (adapter.countries.size() == 0) {
            adapter.fetchPage();
        }*/
        fetchPage();

        return binding.getRoot();
    }

    private void fetchPage() {
        regionStatsViewModel.getCountries().observe(getViewLifecycleOwner(), new Observer<Countries>() {
            @Override
            public void onChanged(Countries countries) {
                if (countries != null) {
                    adapter.lastUpdate = countries.data.lastUpdate;
                    // Utils.updateAdapter(adapter, countries.data.countries);
                    adapter.update(countries.data.countries);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                Utils.shareScreenShot(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
