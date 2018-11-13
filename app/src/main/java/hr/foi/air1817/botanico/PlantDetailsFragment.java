package hr.foi.air1817.botanico;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import hr.foi.air1817.botanico.entities.Plant;

import static android.support.constraint.Constraints.TAG;

public class PlantDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.plant_details_fragment, container, false);
        return v;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onStart() {
        super.onStart();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.nav_plant_details);

        final ImageView plantImage = getView().findViewById(R.id.plantImage);
        final TextView temp = getView().findViewById(R.id.temperature_data);
        final TextView hum = getView().findViewById(R.id.humidity_data);
        final TextView lux = getView().findViewById(R.id.light_data);

        final Bundle data = getArguments();
        int id = (int) data.get("id");

        DatabaseReference loadData = FirebaseDatabase.getInstance().getReference( "/" + id);

        loadData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Plant plant = dataSnapshot.getValue(Plant.class);
                hum.setText(Float.toString(plant.getHumidity()));
                lux.setText(Float.toString(plant.getLight()));
                temp.setText(Float.toString(plant.getTemp()));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        StorageReference loadImage = FirebaseStorage.getInstance().getReference( id + "/avatar_image/avatar.jpg");

        loadImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){

            @Override
            public void onSuccess(Uri uri) {
                Picasso.with((FragmentActivity) getContext()).load(uri.toString()).into(plantImage);
            }
        });

    }


}
