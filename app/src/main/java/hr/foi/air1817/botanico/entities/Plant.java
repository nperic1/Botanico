package hr.foi.air1817.botanico.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "plant_table")
public class Plant {
    @PrimaryKey
    @NonNull
    private int id;
    private String name;
    private float temp;
    private float humidity;
    private float light;
    private int imageCounter;

    public Plant(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Ignore
    public Plant(){

    }

    public int getImageCounter() {
        return imageCounter;
    }

    public void setImageCounter(int imageCounter) {
        this.imageCounter = imageCounter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void update(Plant plant){
        this.humidity = plant.getHumidity();
        this.temp = plant.getTemp();
        this.light = plant.getLight();
        this.imageCounter = plant.getImageCounter();
    }

    public boolean equals(Plant plant){
        if(this.light == plant.getLight() &&
           this.humidity == plant.getHumidity() &&
           this.temp == plant.getTemp() &&
            this.imageCounter == plant.getImageCounter())
        {
            return true;
        }else {
            return false;
        }
    }
}
