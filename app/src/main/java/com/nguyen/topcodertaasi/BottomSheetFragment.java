package com.nguyen.topcodertaasi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.nguyen.topcodertaasi.databinding.FragmentBottomSheetBinding;
import com.nguyen.topcodertaasi.model.Country;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    public static String TAG = "BottomSheetDialogFragment";
    public static String PARAM_COUNTRY = TAG + ":" + "Country";

    private FragmentBottomSheetBinding binding;
    private Country country;

    public static BottomSheetFragment newInstance(Country country) {
        BottomSheetFragment fragment = new BottomSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAM_COUNTRY, country);
        fragment.setArguments(args);
        return fragment;
    }

    public BottomSheetFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        country = (Country)getArguments().getSerializable(PARAM_COUNTRY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        PieDataSet pieDataSet = new PieDataSet(getData(),"");
        int[] colors = {
                getColor(R.color.color_infected), getColor(R.color.color_recovered), getColor(R.color.color_dead)
        };
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        binding.pieChart.getDescription().setEnabled(false);
        binding.pieChart.getLegend().setEnabled(false);
        binding.pieChart.setData(pieData);
        // pieChart.animateXY(5000, 5000);
        binding.pieChart.invalidate();

        // Picasso.with(getContext()).load(country.flag).into(binding.bottomTitleFlag);
        binding.bottomTitleCountry.setText(country.country);

        binding.bottomInfoTotal.setText(Utils.toString(country.totalCases));
        binding.bottomInfoInfected.setText(Utils.toString(country.activeCases));
        binding.bottomInfoRecovered.setText(Utils.toString(country.totalRecovered));
        binding.bottomInfoDead.setText(Utils.toString(country.totalDeaths));

        return view;
    }

    private int getColor(int resource) {
        // return getResources().getColor(resource);
        return ContextCompat.getColor(getContext(), resource);
    }

    public static void show(Context context, Country country) {
        BottomSheetFragment fragment = newInstance(country);
        fragment.show(((MainActivity)context).getSupportFragmentManager(), fragment.getTag());
    }

    private List<PieEntry> getData(){
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(toFloat(country.activeCases), "Infected"));
        entries.add(new PieEntry(toFloat(country.totalRecovered), "Recovered"));
        entries.add(new PieEntry(toFloat(country.totalDeaths), "Dead"));
        return entries;
    }

    private float toFloat(String number) {
        if (number.equals("N/A")) {
            return 0;
        } else {
            return Float.parseFloat(number.replaceAll(",", ""));
        }
    }

    private float toFloat(int number) {
        try {
            return (float)number;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0f;
        }
    }
}
