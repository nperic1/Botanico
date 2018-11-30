package hr.foi.air1817.botanico;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.google.firebase.database.Query;

import java.util.List;

import hr.foi.air1817.botanico.entities.Plant;

public class PlantViewModel extends AndroidViewModel {
    private PlantRepository repository;
    private LiveData<List<Plant>> allPlants;

    public PlantViewModel(@NonNull Application application) {
        super(application);
        repository = new PlantRepository(application);
        allPlants = repository.getAllPlants();
    }

    public void insert(Plant plant){
        repository.insert(plant);
    }

    public void update(Plant plant){
        repository.update(plant);
    }

    public Plant findById(int id){
        return  repository.findPlantById(id);
    }

    public LiveData<List<Plant>> getAllPlants(){
        return allPlants;
    }

    public void deleteAll(){
        repository.deleteAll();
    }

}
