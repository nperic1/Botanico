package hr.foi.air1817.botanico;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import hr.foi.air1817.botanico.entities.Plant;

public class PlantDetails extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden_view);

        PlantViewModel viewModel = ViewModelProviders.of(this).get(PlantViewModel.class);
        LiveData<DataSnapshot> liveData = viewModel.getDataSnapshotLiveData();

        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    Plant plant = dataSnapshot.getValue(Plant.class);
                    // TODO bind data
                }
            }
        });
    }

    public void goBack(View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

}
