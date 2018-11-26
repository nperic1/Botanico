package hr.foi.air1817.botanico;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


import hr.foi.air1817.botanico.entities.Plant;

@Database(entities = {Plant.class}, version = 1, exportSchema = false)
public abstract class PlantRoomDatabase extends RoomDatabase {
    private static PlantRoomDatabase INSTANCE;

    public abstract PlantDao plantDao();
    public abstract PlantHistoryDao plantHistoryDao();

    public static PlantRoomDatabase getPlantRoomDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), PlantRoomDatabase.class, "plant-database")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
