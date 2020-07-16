package com.nguyen.topcodertaasi.ui.region_stats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nguyen.topcodertaasi.model.Countries;
import com.nguyen.topcodertaasi.model.CovidRepository;

public class RegionStatsViewModel extends ViewModel {

    private static final String TAG = "RegionStatsViewModel";

    public RegionStatsViewModel() {
    }

    public LiveData<Countries> getCountries() {
        return CovidRepository.getInstance().getCountries();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}