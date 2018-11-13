package hr.foi.air1817.botanico.entities;

public class Plant {
    private int id;
    private String name;
    private float last_temp;
    private float last_humidity;
    private float last_light;
    private String image_path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plant(int id, String name, float last_temp, float last_humidity, float last_light, String image_path) {
        this.id = id;
        this.name = name;
        this.last_temp = last_temp;
        this.last_humidity = last_humidity;
        this.last_light = last_light;
        this.image_path = image_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLast_temp() {
        return last_temp;
    }

    public void setLast_temp(float last_temp) {
        this.last_temp = last_temp;
    }

    public float getLast_humidity() {
        return last_humidity;
    }

    public void setLast_humidity(float last_humidity) {
        this.last_humidity = last_humidity;
    }

    public float getLast_light() {
        return last_light;
    }

    public void setLast_light(float last_light) {
        this.last_light = last_light;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
