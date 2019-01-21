package com.example.watering;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
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
    private Button saveChanges;
    private Switch automaticWateringSwitch;
    private Switch scheduledWateringSwitch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.garden_settings_fragment, container, false);
    }

    public void onStart() {
        super.onStart();
        minMoisture = getActivity().findViewById(R.id.edit_min_moisture);
        wateringTime = getActivity().findViewById(R.id.scheduled_watering_time);
        saveChanges = getActivity().findViewById(R.id.save_watering_settings);
        automaticWateringSwitch = getActivity().findViewById(R.id.automatic_watering_switch);
        scheduledWateringSwitch = getActivity().findViewById(R.id.scheduled_watering_switch);

        loadScheduledWatering.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PlantScheduledWatering plantScheduledWatering = dataSnapshot.getValue(PlantScheduledWatering.class);
                scheduledWateringSwitch.setChecked(plantScheduledWatering.status);
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
                automaticWateringSwitch.setChecked(plantAutomaticWatering.status);
                minMoisture.setText(String.valueOf(plantAutomaticWatering.min_humidity));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAutomaticWatering(minMoisture.getText().toString());
                //updateScheduledWatering(wateringTime.getText().toString());
                //TODO dialog o upjesnim promijenama
            }
        });

        scheduledWateringSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    FirebaseDatabase.getInstance().getReference( "/235112/scheduled_watering")
                                    .child("status")
                                    .setValue(true);
                } else {
                    FirebaseDatabase.getInstance().getReference( "/235112/scheduled_watering")
                                    .child("status")
                                    .setValue(false);
                }
            }
        });

        automaticWateringSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FirebaseDatabase.getInstance().getReference( "/235112/automatic_watering")
                            .child("status")
                            .setValue(true);
                } else {
                    FirebaseDatabase.getInstance().getReference( "/235112/automatic_watering")
                            .child("status")
                            .setValue(false);
                }
            }
        });
    }


    public void updateAutomaticWatering(String humidity){
        FirebaseDatabase.getInstance().getReference( "/235112/automatic_watering")
                        .child("min_humidity")
                        .setValue(Float.parseFloat(humidity));
    }

    public void updateScheduledWatering(String time){
        FirebaseDatabase.getInstance().getReference( "/235112/scheduled_watering")
                .child("min_humidity")
                .setValue(time);
    }
}
