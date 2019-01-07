package hr.foi.air1817.botanico.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hr.foi.air1817.botanico.NavigationItem;
import hr.foi.air1817.botanico.R;

public class HelpFragment extends Fragment implements NavigationItem {
    private int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.help_fragment, container, false);
        return v;
    }

    @Override
    public String getItemName(Context context) {
        return context.getString(R.string.nav_help);
    }

    @Override
    public Drawable getIcon(Context context) {
        return context.getDrawable(R.drawable.ic_help);
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
    public android.app.Fragment getFragment() {
        return this;
    }
}
