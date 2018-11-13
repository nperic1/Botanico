package hr.foi.air1817.botanico.entities;

public class Plant {
    private int id;
    private String name;
    private float temp;
    private float humidity;
    private float light;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plant(int id, String name, float temp, float humidity, float light) {
        this.id = id;
        this.name = name;
        this.temp = temp;
        this.humidity = humidity;
        this.light = light;
    }

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

    public void setTemp(float last_temp) {
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

    public String getAvatarPath(){return "/" + id + "/avatar_image/";};
}
