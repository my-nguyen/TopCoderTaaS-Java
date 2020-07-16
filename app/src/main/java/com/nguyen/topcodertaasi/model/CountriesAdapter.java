package com.nguyen.topcodertaasi.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nguyen.topcodertaasi.BottomSheetFragment;
import com.nguyen.topcodertaasi.R;
import com.nguyen.topcodertaasi.Utils;
import com.nguyen.topcodertaasi.databinding.ItemCountryBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemCountryBinding binding;

        public ViewHolder(ItemCountryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(this);
        }

        public void bind(Country country) {
            Picasso.get().load(country.flag).into(binding.countryFlag);
            binding.countryName.setText(country.country);
            String text = context.getResources().getString(R.string.label_last_update, CountriesAdapter.this.lastUpdate);
            binding.countryLastUpdate.setText(text);
            binding.countryCount.setText(Utils.toString(country.totalCases));
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Country country = CountriesAdapter.this.countries.get(position);
                BottomSheetFragment.show(context, country);
            }
        }
    }

    public static CountriesAdapter getInstance() {
        if (instance == null) {
            instance = new CountriesAdapter();
        }
        return instance;
    }

    public String lastUpdate;
    public List<Country> countries;

    private static final String TAG = "CountriesAdapter";
    private static CountriesAdapter instance;

    private Context context;

    private CountriesAdapter() {
        countries = new ArrayList();
        lastUpdate = null;
        context = null;
    }

    public void update(List<Country> countries) {
        int size = getItemCount();
        this.countries.addAll(countries);
        notifyItemRangeInserted(size, countries.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemCountryBinding binding = ItemCountryBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Country country = this.countries.get(position);
        holder.bind(country);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }
}
