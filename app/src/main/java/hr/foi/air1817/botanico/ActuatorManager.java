package hr.foi.air1817.botanico;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.core.ActuatorItem;

import java.util.ArrayList;



public class ActuatorManager {
    public ArrayList<ActuatorItem> items;
    private static ActuatorManager instance = null;

    private ActuatorManager(){
        items = new ArrayList<>();
    }

    public static ActuatorManager getInstance(){
        if(instance == null) instance = new ActuatorManager();
        return instance;
    }

    public void addItem(final ActuatorItem item, final int id, Activity context){
        items.add(item);
        LinearLayout container = context.findViewById(R.id.actuatorContainer);
        int buttonLayoutId = item.getButton();
        View v = context.getLayoutInflater().inflate(buttonLayoutId, null);
        container.addView(v);
        v.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     Bundle args = new Bundle();
                                     args.putInt("id", id);
                                     Fragment gsf = item.getFragment();
                                     gsf.setArguments(args);
                                     NavigationManager.getInstance().switchFragment(gsf, "details");
                                 }
                             }
        );
    }
}