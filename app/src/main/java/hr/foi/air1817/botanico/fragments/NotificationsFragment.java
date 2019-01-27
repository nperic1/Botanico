package hr.foi.air1817.botanico.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.core.NavigationItem;
import hr.foi.air1817.botanico.R;
import hr.foi.air1817.botanico.firebaseMessaging.BotanicoNotificationManager;

public class NotificationsFragment extends Fragment implements NavigationItem {
    private Switch low_moisture;
    private Switch unplanned_watering;
    private Switch temperature;
    private Switch watering;

    private int position;

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

        watering = getActivity().findViewById(R.id.switch_watering);
        low_moisture = getActivity().findViewById(R.id.switch_low_moisture);
        unplanned_watering = getActivity().findViewById(R.id.switch_unplanned_watering);
        temperature = getActivity().findViewById(R.id.switch_temperature);

        getPreferences(sharedPreferences);

        watering.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("watering", isChecked).commit();
                if(isChecked) BotanicoNotificationManager.getInstance(getActivity()).subscribeToTopic("watering");
                else BotanicoNotificationManager.getInstance(getActivity()).unsubscribeFromTopic("watering");
            }
        });

        low_moisture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("low_moisture", isChecked).commit();
                if(isChecked) BotanicoNotificationManager.getInstance(getActivity()).subscribeToTopic("low_moisture");
                else BotanicoNotificationManager.getInstance(getActivity()).unsubscribeFromTopic("low_moisture");
            }
        });

        unplanned_watering.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("unplanned_watering", isChecked).commit();
                if(isChecked) BotanicoNotificationManager.getInstance(getActivity()).subscribeToTopic("unplanned_watering");
                else BotanicoNotificationManager.getInstance(getActivity()).unsubscribeFromTopic("unplanned_watering");
            }
        });

        temperature.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("temperature", isChecked).commit();
                if(isChecked) BotanicoNotificationManager.getInstance(getActivity()).subscribeToTopic("temperature");
                else BotanicoNotificationManager.getInstance(getActivity()).unsubscribeFromTopic("temperature");
            }
        });
    }

    private void getPreferences(SharedPreferences sharedPreferences){
        watering.setChecked(sharedPreferences.getBoolean("watering", false));
        low_moisture.setChecked(sharedPreferences.getBoolean("low_moisture",false));
        unplanned_watering.setChecked(sharedPreferences.getBoolean("unplanned_watering",false));
        temperature.setChecked(sharedPreferences.getBoolean("temperature",false));
    }

    @Override
    public String getItemName(Context context) {
        return context.getString(R.string.nav_notification_settings);
    }

    @Override
    public Drawable getIcon(Context context) {
        return context.getDrawable(R.drawable.ic_notifications);
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
