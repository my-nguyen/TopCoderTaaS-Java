package com.nguyen.topcodertaasi.ui.covid_map;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nguyen.topcodertaasi.BottomSheetFragment;
import com.nguyen.topcodertaasi.R;
import com.nguyen.topcodertaasi.Utils;
import com.nguyen.topcodertaasi.model.Coordinates;
import com.nguyen.topcodertaasi.model.CountriesAdapter;
import com.nguyen.topcodertaasi.model.Country;

import java.io.File;
import java.util.List;

public class CovidMapFragment extends Fragment implements OnMapReadyCallback {

    private CovidMapViewModel covidMapViewModel;
    private GoogleMap map;
    private File snapShotFile = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        covidMapViewModel = new ViewModelProvider(this).get(CovidMapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_covid_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                if (snapShotFile != null) {
                    Utils.shareImage(CovidMapFragment.this, snapShotFile);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        Log.d("TRUONG", "CovidMapFragment.onMapReady");

        covidMapViewModel.getAllCountries().observe(getViewLifecycleOwner(), new Observer<List<Country>>() {
            @Override
            public void onChanged(List<Country> countries) {
                Log.d("TRUONG", "CovidMapFragment.onMapReady.getAllCountries.observe.onChanged");
                // load all country lat-long coordinates from CSV file
                Coordinates coordinates = Coordinates.getInstance(getContext(), "countries.csv");
                LatLng firstLatLng = null;
                CountriesAdapter adapter = CountriesAdapter.getInstance();
                // Utils.updateAdapter(adapter, countries);
                adapter.update(countries);
                // iterate thru adapter countries
                for (int i = 0; i < adapter.countries.size(); i++) {
                    // find lat-long based on country name abbreviation
                    LatLng latLng = coordinates.toLatLng(adapter.countries.get(i).countryAbbreviation);
                    if (latLng != null) {
                        // make a marker from lat-long found
                        Marker marker = map.addMarker(new MarkerOptions().position(latLng).title(adapter.countries.get(i).country));
                        // record country index in marker
                        marker.setTag(i);
                        // save the first lat-lng if necessary
                        if (firstLatLng == null) {
                            firstLatLng = latLng;
                        }
                    }
                }
                // move the camera to the first country in the list, which is USA
                map.moveCamera(CameraUpdateFactory.newLatLng(firstLatLng));
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        // retrieve from marker index of country selected
                        int index = (int)marker.getTag();
                        // show bottom sheet of country selected
                        BottomSheetFragment.show(getContext(), adapter.countries.get(index));
                        return false;
                    }
                });

                // take a snapshot of the google map and save it into file
                map.snapshot(new GoogleMap.SnapshotReadyCallback() {
                    @Override
                    public void onSnapshotReady(Bitmap bitmap) {
                        snapShotFile = Utils.saveBitmap(getContext(), bitmap);
                    }
                });
            }
        });
    }
}
