package hr.foi.air1817.botanico;

import android.app.Fragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import java.util.List;

import butterknife.ButterKnife;
import hr.foi.air1817.botanico.entities.Plant;

public class PlantDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.plant_details_fragment, container, false);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle data = getArguments();
        int id = (int) data.get("id");
        Log.i("plant", Integer.toString(id));;

        TextView temp = getView().findViewById(R.id.temperature_data);
        TextView hum = getView().findViewById(R.id.humidity_data);
        TextView lux = getView().findViewById(R.id.light_data);

        /*temp.setText(Float.toString(p.getLast_temp()));
        hum.setText(Float.toString(p.getLast_humidity()));
        lux.setText(Float.toString(p.getLast_light()));*/
        //TODO Dohvatiti biljku pomoću ID-a i prikazat podatke
        temp.setText("3");
        hum.setText("5");
        lux.setText("6");
    }


    /*@RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_details_fragment);

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
    }*/

}
