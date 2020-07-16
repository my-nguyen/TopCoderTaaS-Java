package com.nguyen.topcodertaasi.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CovidRepository {
    private static final String TAG = "CovidRepository";
    public static final String BASE_URL = "https://corona-virus-stats.herokuapp.com/api/v1/cases/";
    private static final CovidRepository instance = new CovidRepository();

    private CovidAPI covidAPI;
    private int currentPage;

    private World _world;

    public static CovidRepository getInstance() {
        return instance;
    }

    private CovidRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        covidAPI = retrofit.create(CovidAPI.class);
        currentPage = 1;
        _world = null;
    }

    public LiveData<World> getWorld() {
        final MutableLiveData<World> world = new MutableLiveData<>();
        if (_world != null) {
            world.setValue(_world);
        } else {
            covidAPI.getWorld().enqueue(new Callback<World>() {
                @Override
                public void onResponse(@NonNull Call<World> call, @NonNull Response<World> response) {
                    _world = response.body();
                    world.setValue(response.body());
                }

                @Override
                public void onFailure(@NonNull Call<World> call, @NonNull Throwable t) {
                    Log.d(TAG, "onFailure: failed to fetch World");
                }
            });
        }
        return world;
    }

    public LiveData<Countries> getCountries() {
        final MutableLiveData<Countries> countries = new MutableLiveData<>();
        covidAPI.getCountries("total_cases", currentPage).enqueue(new Callback<Countries>() {
            @Override
            public void onResponse(Call<Countries> call, Response<Countries> response) {
                countries.setValue(response.body());
                currentPage++;
            }

            @Override
            public void onFailure(Call<Countries> call, Throwable t) {
                Log.d(TAG, "onFailure: failed to fetch Countries");
            }
        });
        return countries;
    }

    List<Country> countries = new ArrayList();
    public LiveData<List<Country>> getAllCountries() {
        MutableLiveData<List<Country>> allCountries = new MutableLiveData<>();
        covidAPI.getCountries("total_cases", currentPage).enqueue(new Callback<Countries>() {
            @Override
            public void onResponse(Call<Countries> call, Response<Countries> response) {
                // if (currentPage < response.body().data.pagination.totalPages) {
                if (response.body() != null) {
                    Log.d("TRUONG", "CovidRepository.getAllCountries.getCountries.onResponse, currentPage: " + currentPage);
                    currentPage++;
                    countries.addAll(response.body().data.countries);
                    getAllCountries();
                } else {
                    Log.d("TRUONG", "CovidRepository.getAllCountries.getCountries.onResponse, setValue");
                    allCountries.setValue(countries);
                }
            }

            @Override
            public void onFailure(Call<Countries> call, Throwable t) {
            }
        });
        return allCountries;
    }
}
