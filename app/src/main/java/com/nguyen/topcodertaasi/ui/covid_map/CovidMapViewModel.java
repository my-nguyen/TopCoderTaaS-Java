package com.nguyen.topcodertaasi.ui.covid_map;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nguyen.topcodertaasi.model.Country;
import com.nguyen.topcodertaasi.model.CovidRepository;

import java.util.List;

public class CovidMapViewModel extends ViewModel {

    private static final String TAG = "CovidMapViewModel";

    private LiveData<List<Country>> data;

    public CovidMapViewModel() {
        data = CovidRepository.getInstance().getAllCountries();
    }

    public LiveData<List<Country>> getAllCountries() {
        Log.d("TRUONG", "CovidMapViewModel.getAllCountries");
        return data;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}