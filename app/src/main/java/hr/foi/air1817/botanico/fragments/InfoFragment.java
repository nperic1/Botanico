package hr.foi.air1817.botanico.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hr.foi.air1817.botanico.NavigationItem;
import hr.foi.air1817.botanico.R;

public class InfoFragment extends android.app.Fragment implements NavigationItem {

    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tv = getActivity().findViewById(R.id.ack);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AcknowledgementsFragment newFr = new AcknowledgementsFragment();
                FragmentTransaction fm = getFragmentManager().beginTransaction();
                fm.replace(R.id.fragment_container, newFr);
                fm.addToBackStack("");
                fm.commit();
            }
        });
    }

    @Override
    public String getItemName(Context context) {
        return context.getString(R.string.nav_info);
    }

    @Override
    public Drawable getIcon(Context context) {
        return context.getDrawable(R.drawable.ic_info);
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
