package com.nguyen.topcodertaasi.ui.total_stats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nguyen.topcodertaasi.model.CovidRepository;
import com.nguyen.topcodertaasi.model.World;

public class TotalStatsViewModel extends ViewModel {
    private static final String TAG = "TotalStatsViewModel";

    private LiveData<World> data;

    public TotalStatsViewModel() {
        data = CovidRepository.getInstance().getWorld();
    }

    public LiveData<World> getWorld() {
        return data;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}