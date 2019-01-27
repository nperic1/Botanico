package com.example.core;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;

public interface NavigationItem {
    public String getItemName(Context context);
    public Drawable getIcon(Context context);
    public int getPosition();
    public void setPosition(int position);
    public Fragment getFragment();
}
