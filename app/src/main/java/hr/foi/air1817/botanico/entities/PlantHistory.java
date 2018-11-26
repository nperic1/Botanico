package hr.foi.air1817.botanico.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Plant.class,
                                  parentColumns = "id",
                                  childColumns = "plantId",
                                  onDelete = CASCADE),
        tableName = "plant_history_table")

public class PlantHistory {
    @PrimaryKey private int id;
    private float temp;
    private float humidity;
    private float light;
    private int plantId;

    public PlantHistory(int id, float temp, float humidity, float light, int plantId) {
        this.id = id;
        this.temp = temp;
        this.humidity = humidity;
        this.light = light;
        this.plantId = plantId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getLight() {
        return light;
    }

    public void setLight(float light) {
        this.light = light;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }
}
