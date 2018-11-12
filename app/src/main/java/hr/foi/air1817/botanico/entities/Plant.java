package hr.foi.air1817.botanico.entities;

public class Plant {
    private int id;
    private String name;
    private double temp;
    private double humidity;
    private double light;
    private String image_path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plant(int id, String name, double temp, double humidity, double light, String image_path) {
        this.id = id;
        this.name = name;
        this.temp = temp;
        this.humidity = humidity;
        this.light = light;
        this.image_path = image_path;
    }

    public Plant(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getLight() {
        return light;
    }

    public void setLight(double light) {
        this.light = light;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
