package hr.foi.air1817.botanico.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hr.foi.air1817.botanico.NavigationItem;
import hr.foi.air1817.botanico.R;

public class GalleryFragment extends Fragment implements NavigationItem {

    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.gallery_fragment, container, false);
        return v;
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
