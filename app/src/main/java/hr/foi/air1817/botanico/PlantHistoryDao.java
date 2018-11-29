package hr.foi.air1817.botanico;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import hr.foi.air1817.botanico.entities.PlantHistory;

@Dao
public interface PlantHistoryDao {
    @Insert
    void insert(PlantHistory plantHistory);

    @Delete
    void delete(PlantHistory... plantHistory);

    @Query("DELETE FROM plant_history_table")
    void deleteAll();

    @Query("SELECT * FROM plant_history_table")
    List<PlantHistory> getAllPlantHistories();

    @Query("SELECT * from plant_history_table WHERE plantId=:plantId")
    List<PlantHistory> findHistoryForPlant(final int plantId);
}
