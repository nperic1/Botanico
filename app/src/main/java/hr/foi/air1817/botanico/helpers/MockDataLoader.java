package hr.foi.air1817.botanico.helpers;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import hr.foi.air1817.botanico.PlantRoomDatabase;
import hr.foi.air1817.botanico.entities.Plant;

public class MockDataLoader {
    /*
    public static List<Plant> getDemoData(){
        List<Plant> mPlants = new ArrayList<Plant>();
        mPlants.add(new Plant(235112,"Tulips",34,3,4));
        mPlants.add(new Plant(611221,"Salad",34,20,500));
        return mPlants;
    }*/

    public static List<Plant> getDemoData(PlantRoomDatabase db) {
            Plant plant1 = new Plant(235112, "Salads");
            Plant plant2 = new Plant(611221, "Tulips");
            addPlant(db, plant1);
            addPlant(db, plant2);

        return db.plantDao().getAllPlants();
}

    private static Plant addPlant(final PlantRoomDatabase db, Plant plant) {
        db.plantDao().insert(plant);
        return plant;
    }
}
