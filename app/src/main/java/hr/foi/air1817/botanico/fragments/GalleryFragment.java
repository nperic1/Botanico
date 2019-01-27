package hr.foi.air1817.botanico.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import hr.foi.air1817.botanico.NavigationItem;
import hr.foi.air1817.botanico.PlantRoomDatabase;
import hr.foi.air1817.botanico.R;
import hr.foi.air1817.botanico.adapters.GalleryGridAdapter;
import hr.foi.air1817.botanico.entities.Plant;

import static android.app.Activity.RESULT_OK;

public class GalleryFragment extends Fragment implements NavigationItem {
    private DatabaseReference mDatabaseRef;

    public List<Plant> listaBiljaka;
    public StorageReference imageRef;
    public Button slikajMolimTe;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private int position;
    GridView galleryGridView;
    public ArrayList<Bitmap> sveSlike = new ArrayList<Bitmap>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.gallery_fragment, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        listaBiljaka=PlantRoomDatabase.getPlantRoomDatabase(getActivity()).plantDao().getAllPlants();
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        galleryGridView = (GridView)view.findViewById(R.id.gallery_gridview);
        galleryGridView.setAdapter(new GalleryGridAdapter(getActivity(), sveSlike));
        slikajMolimTe = (Button) view.findViewById(R.id.new_picture_button);
        slikajMolimTe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });



    }

    public void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            sveSlike.add(imageBitmap);
            galleryGridView.invalidateViews();
        }
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
    }

    @Override
    public String getItemName(Context context) {
        return context.getString(R.string.nav_gallery);
    }

    @Override
    public Drawable getIcon(Context context) {
        return context.getDrawable(R.drawable.ic_image);
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }
}
