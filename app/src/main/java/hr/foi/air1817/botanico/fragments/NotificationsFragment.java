package hr.foi.air1817.botanico.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import hr.foi.air1817.botanico.R;

public class NotificationsFragment extends Fragment {
    Switch notifications;
    Switch moisture;
    Switch watering;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.notifications_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("notifications", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        //notifications = getActivity().findViewById(R.id.switch_notifications);
        moisture = getActivity().findViewById(R.id.switch_moisture);
        watering = getActivity().findViewById(R.id.switch_watering);

        getPreferences(sharedPreferences);

        /*notifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("notifications", isChecked).commit();
            }
        });*/

        moisture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("moisture", isChecked).commit();
            }
        });

        watering.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("watering", isChecked).commit();
            }
        });
    }

    private void getPreferences(SharedPreferences sharedPreferences){
        //notifications.setChecked(sharedPreferences.getBoolean("notifications", true));
        moisture.setChecked(sharedPreferences.getBoolean("moisture", true));
        watering.setChecked(sharedPreferences.getBoolean("watering", true));
    }

}
