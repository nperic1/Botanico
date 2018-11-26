package hr.foi.air1817.botanico.helpers;

import java.util.List;

import hr.foi.air1817.botanico.PlantRoomDatabase;
import hr.foi.air1817.botanico.entities.Plant;

public class MockDataLoader {

    public static List<Plant> getDemoData(PlantRoomDatabase db) {
            /*Plant plant1 = new Plant(235112, "Tulips");
            Plant plant2 = new Plant(611221, "Salads");
            addPlant(db, plant1);
            addPlant(db, plant2);*/

            //TODO obrisi pomocnu klasu

        return db.plantDao().getAllPlants();
}

    private static Plant addPlant(final PlantRoomDatabase db, Plant plant) {
        db.plantDao().insert(plant);
        return plant;
    }
}
