package hr.foi.air1817.botanico;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

public class PlantDetails extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden_view);

        PlantViewModel viewModel = ViewModelProviders.of(this).get(PlantViewModel.class);
        LiveData<DataSnapshot> liveData = viewModel.getDataSnapshotLiveData();

        final TextView mHumidity = (TextView)findViewById(R.id.humidity_data);
        final TextView mTemperature = (TextView)findViewById(R.id.temperature_data);
        final TextView mLight = (TextView)findViewById(R.id.light_data);

        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    // update the UI here with values in the snapshot
                    Long temp = dataSnapshot.child("temp").getValue(Long.class);
                    Long humidity = dataSnapshot.child("humidity").getValue(Long.class);
                    Long light = dataSnapshot.child("light").getValue(Long.class);
                    mHumidity.setText(humidity.toString() + " %");
                    mTemperature.setText(temp.toString() + " C");
                    mLight.setText(light.toString() + " LUX");
                }
            }
        });
    }

    public void goBack(View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

}
