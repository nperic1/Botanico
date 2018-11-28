package hr.foi.air1817.botanico;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;


import hr.foi.air1817.botanico.entities.Plant;
import hr.foi.air1817.botanico.entities.PlantHistory;
import hr.foi.air1817.botanico.helpers.Converters;

@Database(entities = {Plant.class, PlantHistory.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class PlantRoomDatabase extends RoomDatabase {
    private static PlantRoomDatabase INSTANCE;

    public abstract PlantDao plantDao();
    public abstract PlantHistoryDao plantHistoryDao();

    public static PlantRoomDatabase getPlantRoomDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), PlantRoomDatabase.class, "plant-database")
                            .addMigrations(FROM_1_TO_2, FROM_2_TO_3)
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    static final Migration FROM_1_TO_2 = new Migration(1, 2)
    {
        @Override
        public void migrate(final SupportSQLiteDatabase database)
        {
            database.execSQL("ALTER TABLE plant_history_table ADD date INTEGER; ");
        }
    };

    static final Migration FROM_2_TO_3 = new Migration(2, 3)
    {
        @Override
        public void migrate(final SupportSQLiteDatabase database)
        {
            database.execSQL("CREATE INDEX index_plantId ON plant_history_table (plantId)");
        }
    };


    public static void destroyInstance() {
        INSTANCE = null;
    }

}
