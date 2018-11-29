package hr.foi.air1817.botanico;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import hr.foi.air1817.botanico.entities.Plant;
@Dao
public interface PlantDao {
    @Insert
    void insert(Plant plant);

    @Query("SELECT * from plant_table WHERE id=:id")
    Plant findPlantById(final int id);

    @Update
    void update(Plant plant);

    @Query("DELETE FROM plant_table")
    void deleteAll();

    @Query("SELECT * from plant_table ")
    List<Plant> getAllPlants();
}
