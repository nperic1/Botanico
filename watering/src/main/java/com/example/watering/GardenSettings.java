package com.example.watering;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class GardenSettings extends Fragment {

    DatabaseReference loadScheduledWatering = FirebaseDatabase.getInstance().getReference( "/235112/scheduled_watering");
    DatabaseReference loadAutomaticWatering = FirebaseDatabase.getInstance().getReference( "/235112/automatic_watering");
    private TextInputEditText minMoisture;
    private TextView wateringTime;

    public void onStart() {
        super.onStart();
        minMoisture = getActivity().findViewById(R.id.edit_min_moisture);
        wateringTime = getActivity().findViewById(R.id.scheduled_watering_time);

        loadScheduledWatering.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PlantScheduledWatering plantScheduledWatering = dataSnapshot.getValue(PlantScheduledWatering.class);
                wateringTime.setText(plantScheduledWatering.time);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        loadAutomaticWatering.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PlantAutomaticWatering plantAutomaticWatering = dataSnapshot.getValue(PlantAutomaticWatering.class);
                minMoisture.setText(String.valueOf(plantAutomaticWatering.min_humidity));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.garden_settings_fragment, container, false);
    }

}
