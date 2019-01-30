package hr.foi.air1817.botanico.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.watering.GardenSettings;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hr.foi.air1817.botanico.ActuatorManager;
import hr.foi.air1817.botanico.NavigationManager;
import hr.foi.air1817.botanico.PlantRepository;
import hr.foi.air1817.botanico.R;
import hr.foi.air1817.botanico.entities.Plant;


public class PlantDetailsFragment extends Fragment {
    final ArrayList<String> dateList = new ArrayList<>();
    final ArrayList<String> moistureList = new ArrayList<>();
    PlantRepository repository;
    int id;

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

        repository = new PlantRepository(getActivity().getApplication());

        final Bundle data = getArguments();
        id = (int) data.get("id");
        getDataForBundle();

        getActuators();

        final DatabaseReference reference  = FirebaseDatabase.getInstance().getReference(String.valueOf(id));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Plant plant = dataSnapshot.getValue(Plant.class);
                syncData(repository.findPlantById(id), plant);
                hum.setText(Float.toString(plant.getHumidity()) + " %");
                lux.setText(Float.toString(plant.getLight()) + " K");
                temp.setText(Float.toString(plant.getTemp()) + " °C");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        StorageReference loadImage = FirebaseStorage.getInstance().getReference( id + "/avatar_image/avatar.jpg");

        loadImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){

            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getContext()).load(uri.toString()).into(plantImage);
            }
        });

        final LinearLayout humidityButton = getActivity().findViewById(R.id.humidity_img);
        humidityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putStringArrayList("moisture", moistureList);
                args.putStringArrayList("date", dateList);
                Fragment mlg = new MoistureLevelGraphFragment();
                mlg.setArguments(args);
                NavigationManager.getInstance().switchFragment(mlg, "moisture_graph");
            }
        });
    }

    private void getDataForBundle() {
        if (dateList.isEmpty() && moistureList.isEmpty()) {
            DatabaseReference loadData = FirebaseDatabase.getInstance().getReference("/" + id + "/history");
            loadData.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        dateList.add(String.valueOf(dsp.child("date").getValue()).substring(0, 2));
                        moistureList.add(String.valueOf(dsp.child("moisture").getValue()));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void getActuators(){
        ActuatorManager am = ActuatorManager.getInstance();
        am.addItem(new GardenSettings(), id, getActivity());
    }

    public void syncData(Plant currentPlant, Plant firebasePlantData) {
        if(!currentPlant.equals(firebasePlantData)){
            currentPlant.update(firebasePlantData);
            update(currentPlant);
        }
    }

    public void update(Plant plant){
        repository.update(plant);
    }
}
