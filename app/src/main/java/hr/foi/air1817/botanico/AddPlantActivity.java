package hr.foi.air1817.botanico;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import hr.foi.air1817.botanico.entities.Plant;

public class AddPlantActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageButton btnChoose;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private StorageReference storageReference;
    private TextInputLayout deviceId;
    private TextInputLayout plantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
        btnChoose=findViewById(R.id.button_choose_image);
        deviceId = findViewById(R.id.new_plant_id);
        plantName = findViewById(R.id.input_plant_name);

        setToolbar();
    }

    public void setToolbar(){
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("New plant");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void chooseImage(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void showDialog(int title, int msg){
        DialogManager.getInstance().showDialogOK(this, title, msg);
    }

    public void showPopUp(View view){
        showDialog(R.string.wifi_setup, R.string.wifi_steps);
    }

    public void uploadImage() {
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            storageReference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    public void addPlant(View view){
        if(deviceId.getEditText().getText().toString().equals("") || plantName.getEditText().getText().toString().equals("")){
            showDialog(R.string.dialog_title_error, R.string.dialog_msg_not_filled);
            return;
        }

        try {
            checkIdExistenceFirebase(new FirebaseCallBack() {
                @Override
                public void onCallBack(boolean result) {
                    if(result && !checkIdExistenceDatabase()){
                        storageReference = FirebaseStorage.getInstance().getReference().child("/" + deviceId.getEditText().getText().toString() +"/avatar_image/avatar.jpg");
                        uploadImage();

                        PlantRoomDatabase db = PlantRoomDatabase.getPlantRoomDatabase(getApplicationContext());
                        Plant plant = new Plant(Integer.parseInt( deviceId.getEditText().getText().toString()), plantName.getEditText().getText().toString());
                        db.plantDao().insert(plant);

                        openMainActivity();
                    }
                    else  if(result && checkIdExistenceDatabase()){
                        //TODO povuć podatke ako id postoji
                    }
                    else {
                        showDialog(R.string.dialog_title_error, R.string.dialog_msg_invalid);
                    }
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                btnChoose.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkIdExistenceFirebase(final FirebaseCallBack firebaseCallBack){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(deviceId.getEditText().getText().toString())){
                    firebaseCallBack.onCallBack(true);
                }else{
                    firebaseCallBack.onCallBack(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private interface FirebaseCallBack{
        void onCallBack(boolean result);
    }

    public boolean checkIdExistenceDatabase(){
        List<Plant> plants = PlantRoomDatabase.getPlantRoomDatabase(getApplicationContext()).plantDao().getAllPlants();
        for(Plant plant: plants){
            if(plant.getId() == Integer.parseInt(deviceId.getEditText().getText().toString())){
                return true;
            }
        }
        return false;
    }
}

