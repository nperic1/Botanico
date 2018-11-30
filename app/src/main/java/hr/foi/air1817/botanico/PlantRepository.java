package hr.foi.air1817.botanico;

import android.app.Application;
import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import hr.foi.air1817.botanico.entities.Plant;
import hr.foi.air1817.botanico.firebase.FirebaseQueryLiveData;

public class PlantRepository {
    private PlantDao plantDao;
    private LiveData<List<Plant>> allPlants;

    FirebaseQueryLiveData firebaseLiveData;
    DatabaseReference reference;

    public PlantRepository(@NonNull Application application){
        PlantRoomDatabase database = PlantRoomDatabase.getPlantRoomDatabase(application);
        plantDao = database.plantDao();
        allPlants = plantDao.getAllLivePlants();
        firebaseLiveData = new FirebaseQueryLiveData(reference);
    }

    public FirebaseQueryLiveData getFirebaseLiveData() {
        return firebaseLiveData;
    }

    public void insert(Plant plant){
        new InsertPlantAsyncTask(plantDao).execute(plant);
    }
    public void deleteAll(){
        new DeleteAllPlantAsyncTask(plantDao).execute();
    }

    public void update(Plant plant){
        new UpdatePlantAsyncTask(plantDao).execute(plant);
    }

    public Plant findPlantById(int id){
        return plantDao.findPlantById(id);
    }

    public LiveData<List<Plant>> getAllPlants(){
        return allPlants;
    }

    private static class InsertPlantAsyncTask extends AsyncTask<Plant, Void, Void>{
        private PlantDao plantDao;

        private InsertPlantAsyncTask(PlantDao plantDao){
            this.plantDao = plantDao;
        }

        @Override
        protected Void doInBackground(Plant... plants) {
            plantDao.insert(plants[0]);
            return null;
        }
    }

    private static class UpdatePlantAsyncTask extends AsyncTask<Plant, Void, Void>{
        private PlantDao plantDao;

        private UpdatePlantAsyncTask(PlantDao plantDao){
            this.plantDao = plantDao;
        }

        @Override
        protected Void doInBackground(Plant... plants) {
            plantDao.update(plants[0]);
            return null;
        }
    }

    private static class DeleteAllPlantAsyncTask extends AsyncTask<Void, Void, Void>{
        private PlantDao plantDao;

        private DeleteAllPlantAsyncTask(PlantDao plantDao){
            this.plantDao = plantDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            plantDao.deleteAll();
            return null;
        }
    }
}
