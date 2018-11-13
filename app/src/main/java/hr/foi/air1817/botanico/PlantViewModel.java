package hr.foi.air1817.botanico;

import android.annotation.TargetApi;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hr.foi.air1817.botanico.entities.Plant;

@TargetApi(Build.VERSION_CODES.N)
@RequiresApi(api = Build.VERSION_CODES.N)
public class PlantViewModel extends ViewModel {
    private static final DatabaseReference PLANT_REF =
            FirebaseDatabase.getInstance().getReference("/235112");

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(PLANT_REF);

    @NonNull
    public LiveData<DataSnapshot> getDataSnapshotLiveData() {
        return liveData;
    }

    private final LiveData<Plant> plantLiveData =
            Transformations.map(liveData, new Deserializer());


    @NonNull
    public LiveData<Plant> getHotStockLiveData() {
        return plantLiveData;
    }

    private class Deserializer implements android.arch.core.util.Function<DataSnapshot, Plant> {
        @Override
        public Plant apply(DataSnapshot input) {
            return null;
        }
    }
}
