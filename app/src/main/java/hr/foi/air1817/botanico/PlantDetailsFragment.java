package hr.foi.air1817.botanico;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import hr.foi.air1817.botanico.entities.Plant;

public class PlantDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.plant_details_fragment, container, false);
        return v;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onStart() {
        super.onStart();

        Bundle data = getArguments();
        int id = (int) data.get("id");
        CurrentPlantPath.path = "/" + id;

        PlantViewModel viewModel = ViewModelProviders.of((FragmentActivity) getContext()).get(PlantViewModel.class);
        LiveData<DataSnapshot> liveData = viewModel.getDataSnapshotLiveData();

        liveData.observe((FragmentActivity) getContext(), new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    Plant currentPlantData = dataSnapshot.getValue(Plant.class);
                }
            }
        });

        TextView temp = getView().findViewById(R.id.temperature_data);
        TextView hum = getView().findViewById(R.id.humidity_data);
        TextView lux = getView().findViewById(R.id.light_data);

    }


}
