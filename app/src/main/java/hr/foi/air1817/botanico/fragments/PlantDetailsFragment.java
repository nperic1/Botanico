package hr.foi.air1817.botanico.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.watering.GardenSettings;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import hr.foi.air1817.botanico.PlantRepository;
import hr.foi.air1817.botanico.PlantViewModel;
import hr.foi.air1817.botanico.helpers.CurrentPlant;
import hr.foi.air1817.botanico.PlantRoomDatabase;
import hr.foi.air1817.botanico.R;
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

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.nav_plant_details);

        final ImageView plantImage = getView().findViewById(R.id.plantImage);
        final TextView temp = getView().findViewById(R.id.temperature_data);
        final TextView hum = getView().findViewById(R.id.humidity_data);
        final TextView lux = getView().findViewById(R.id.light_data);

        final Bundle data = getArguments();
        final int id = (int) data.get("id");
        CurrentPlant.path = Integer.toString(id);

        final PlantViewModel viewModel = ViewModelProviders.of((AppCompatActivity)getActivity()).get(PlantViewModel.class);
        LiveData<Plant> liveData = viewModel.getPlantLiveData();

        final Plant currentPlant = viewModel.findPlantById(id);

        liveData.observe((AppCompatActivity) getActivity(), new Observer<Plant>() {
            @Override
            public void onChanged(@Nullable Plant plant) {
                //TODO bind
                hum.setText(Float.toString(plant.getHumidity()));
                lux.setText(Float.toString(plant.getLight()));
                temp.setText(Float.toString(plant.getTemp()));
            }
        });



        StorageReference loadImage = FirebaseStorage.getInstance().getReference( id + "/avatar_image/avatar.jpg");

        loadImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){

            @Override
            public void onSuccess(Uri uri) {
                Picasso.with((FragmentActivity) getContext()).load(uri.toString()).into(plantImage);
            }
        });
        /**
         * TODO: popravi referencu kod transaction.replace
        final ImageButton gardenSettingsButton = (ImageButton) getView().findViewById(R.id.garden_settings_icon);
        gardenSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Fragment fragment = new GardenSettings();
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nekajtuide, fragment);
                transaction.commit();
                }
            });
        */

    }

}
