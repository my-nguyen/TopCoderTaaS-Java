package com.nguyen.topcodertaasi.ui.total_stats;

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

import com.nguyen.topcodertaasi.R;
import com.nguyen.topcodertaasi.Utils;
import com.nguyen.topcodertaasi.databinding.FragmentTotalStatsBinding;
import com.nguyen.topcodertaasi.model.World;

import static com.nguyen.topcodertaasi.Utils.WRITE_EXTERNAL_STORAGE_PERMISSIONS_REQUEST;

public class TotalStatsFragment extends Fragment {
    private static final String TAG = "TotalStatsFragment";

    private TotalStatsViewModel totalStatsViewModel;
    private FragmentTotalStatsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        totalStatsViewModel = new ViewModelProvider(this).get(TotalStatsViewModel.class);

        binding = FragmentTotalStatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        totalStatsViewModel.getWorld().observe(getViewLifecycleOwner(), new Observer<World>() {
            @Override
            public void onChanged(World data) {
                if (data != null) {
                    binding.confirmedLastUpdate.setText(data.data.lastUpdate);
                    binding.confirmedCount.setText(Utils.toString(data.data.totalCases));
                    binding.infectedLastUpdate.setText(data.data.lastUpdate);
                    binding.infectedCount.setText(Utils.toString(data.data.currentlyInfected));
                    binding.recoveredLastUpdate.setText(data.data.lastUpdate);
                    binding.recoveredCount.setText(Utils.toString(data.data.recoveryCases));
                    binding.deadLastUpdate.setText(data.data.lastUpdate);
                    binding.deadCount.setText(Utils.toString(data.data.deathCases));
                }
            }
        });

        // confirmedIcon.setColorFilter(getResources().getColor(R.color.yellow));
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
                Utils.shareScreenShot(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_PERMISSIONS_REQUEST) {
            Utils.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
