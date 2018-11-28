package hr.foi.air1817.botanico.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Plant.class,
                                  parentColumns = "id",
                                  childColumns = "plantId",
                                  onDelete = CASCADE),
        tableName = "plant_history_table",
        indices = {@Index(value = "plantId")})

public class PlantHistory {
    @PrimaryKey (autoGenerate = true) private int id;
    private float temp;
    private float humidity;
    private float light;
    private int plantId;
    private Date date;

    public PlantHistory(int id, float temp, float humidity, float light, int plantId, Date date) {
        this.id = id;
        this.temp = temp;
        this.humidity = humidity;
        this.light = light;
        this.plantId = plantId;
        this.date = date;
    }

    public PlantHistory(Plant plant) {
        this.id = plant.getId();
        this.temp = plant.getTemp();
        this.humidity = plant.getHumidity();
        this.light = plant.getLight();
        this.plantId = plant.getId();
        this.date = Calendar.getInstance().getTime();
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
