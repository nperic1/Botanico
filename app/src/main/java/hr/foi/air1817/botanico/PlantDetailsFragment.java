package hr.foi.air1817.botanico;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.Fragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import hr.foi.air1817.botanico.entities.Plant;
import hr.foi.air1817.botanico.entities.PlantHistory;
import hr.foi.air1817.botanico.firebase.FirebaseQueryLiveData;

import static android.support.constraint.Constraints.TAG;

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
        final Plant currentPlant = getCurrentPlant(id);

        DatabaseReference loadData = FirebaseDatabase.getInstance().getReference( "/" + id);

        loadData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Plant firebasePlantData = dataSnapshot.getValue(Plant.class);

                if(!currentPlant.equals(firebasePlantData)){ // podaci su se promijenili u Firebasu
                    PlantHistory plantHistory = new PlantHistory(firebasePlantData);
                    plantHistory.setPlantId(currentPlant.getId());
                    PlantRoomDatabase.getPlantRoomDatabase(getContext()).plantHistoryDao().insert(plantHistory);

                    currentPlant.update(firebasePlantData);
                    PlantRoomDatabase.getPlantRoomDatabase(getContext()).plantDao().update(currentPlant);
                }

                //TODO bind
                hum.setText(Float.toString(currentPlant.getHumidity()));
                lux.setText(Float.toString(currentPlant.getLight()));
                temp.setText(Float.toString(currentPlant.getTemp()));

                /*
                //ispis povijesti trenutne biljke
                List<PlantHistory> plantHistoryList = PlantRoomDatabase.getPlantRoomDatabase(getContext()).plantHistoryDao().findHistoryForPlant(currentPlant.getId());
                for(PlantHistory currentPlantHistory: plantHistoryList){
                    Log.d("plant_history", String.valueOf(currentPlantHistory.getId()));
                    Log.d("plant_history", String.valueOf(currentPlantHistory.getHumidity()));
                    Log.d("plant_history", String.valueOf(currentPlantHistory.getLight()));
                    Log.d("plant_history", String.valueOf(currentPlantHistory.getTemp()));
                    Log.d("plant_history", String.valueOf(currentPlantHistory.getDate()));
                }

                //ispis svih biljki
                List<Plant> plantList = PlantRoomDatabase.getPlantRoomDatabase(getContext()).plantDao().getAllPlants();
                for(Plant cpl: plantList){
                    Log.d("plant", String.valueOf(cpl.getId()));
                    Log.d("plant", String.valueOf(cpl.getHumidity()));
                    Log.d("plant", String.valueOf(cpl.getLight()));
                    Log.d("plant", String.valueOf(cpl.getTemp()));
                }*/
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        StorageReference loadImage = FirebaseStorage.getInstance().getReference( id + "/avatar_image/avatar.jpg");

        loadImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){

            @Override
            public void onSuccess(Uri uri) {
                Picasso.with((FragmentActivity) getContext()).load(uri.toString()).into(plantImage);
            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Plant getCurrentPlant(int id){
        return PlantRoomDatabase.getPlantRoomDatabase(getContext()).plantDao().findPlantById(id);
    }

}
