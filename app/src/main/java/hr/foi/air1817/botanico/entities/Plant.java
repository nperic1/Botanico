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

    public int getId() {
        return id;
    }

    public Plant(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Ignore
    public Plant(){

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

}
