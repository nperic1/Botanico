package hr.foi.air1817.botanico.entities;

import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Plant {
    private int id;
    private String name;
    private float temp;
    private float humidity;
    private float light;
    private Uri plantAvatarUri;

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
        setImageAvatarUri();
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

    public Uri getPlantAvatarUri(){return  plantAvatarUri;}

    public void setImageAvatarUri() {
        Task<AuthResult> auth = FirebaseAuth.getInstance().signInAnonymously();
        StorageReference loadImage = FirebaseStorage.getInstance().getReference(id + "/avatar_image/avatar.jpg");
        loadImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                plantAvatarUri = uri;
            }
        });
    }
}
