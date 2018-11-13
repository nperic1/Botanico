package hr.foi.air1817.botanico;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import hr.foi.air1817.botanico.entities.Plant;

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

        final ImageView plantImage = getView().findViewById(R.id.plantImage);
        final TextView temp = getView().findViewById(R.id.temperature_data);
        final TextView hum = getView().findViewById(R.id.humidity_data);
        final TextView lux = getView().findViewById(R.id.light_data);

        Bundle data = getArguments();
        int id = (int) data.get("id");
        CurrentPlantPath.path = "/" + id;

        PlantViewModel viewModel = ViewModelProviders.of((FragmentActivity) getContext()).get(PlantViewModel.class);
        LiveData<DataSnapshot> liveData = viewModel.getDataSnapshotLiveData();

        StorageReference loadImage = FirebaseStorage.getInstance().getReference( id+ "/avatar_image/avatar.jpg");

        loadImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){

            @Override
            public void onSuccess(Uri uri) {
                Picasso.with((FragmentActivity) getContext()).load(uri.toString()).into(plantImage);
            }
        });

        //TODO staviti objekt u bazu
        liveData.observe((FragmentActivity) getContext(), new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    Plant currentPlantData = dataSnapshot.getValue(Plant.class);
                    hum.setText(Float.toString(currentPlantData.getHumidity()));
                    lux.setText(Float.toString(currentPlantData.getLight()));
                    temp.setText(Float.toString(currentPlantData.getTemp()));
                }
            }
        });


    }


}
