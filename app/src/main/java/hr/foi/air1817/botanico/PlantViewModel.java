package hr.foi.air1817.botanico;

import android.annotation.TargetApi;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import hr.foi.air1817.botanico.entities.Plant;
import hr.foi.air1817.botanico.firebase.FirebaseQueryLiveData;
import hr.foi.air1817.botanico.helpers.CurrentPlant;

@TargetApi(Build.VERSION_CODES.N)
@RequiresApi(api = Build.VERSION_CODES.N)
public class PlantViewModel extends AndroidViewModel {
    private PlantRepository repository;

    private static final DatabaseReference REF =
            FirebaseDatabase.getInstance().getReference(CurrentPlant.path);
    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(REF);

    public PlantViewModel(@NonNull Application application) {
        super(application);
        repository = new PlantRepository(application);
    }

    @NonNull
    public LiveData<Plant> getPlantLiveData() {
        return plantLiveData;
    }

    private final LiveData<Plant> plantLiveData =
            Transformations.map(liveData, new Deserializer());

    @RequiresApi(api = Build.VERSION_CODES.N)
    private class Deserializer implements Function<DataSnapshot, Plant>, android.arch.core.util.Function<DataSnapshot, Plant> {
        @Override
        public Plant apply(DataSnapshot dataSnapshot) {
            syncData(findPlantById(Integer.parseInt(CurrentPlant.path)), dataSnapshot.getValue(Plant.class));
            return  dataSnapshot.getValue(Plant.class);
        }
    }

    public void update(Plant plant){
        repository.update(plant);
    }

    public Plant findPlantById(int id){
        return  repository.findPlantById(id);
    }

    public void syncData(Plant currentPlant, Plant firebasePlantData) {
        //TODO add plant_history
        if(!currentPlant.equals(firebasePlantData)){
            currentPlant.update(firebasePlantData);
            update(currentPlant);
        }
    }
}

