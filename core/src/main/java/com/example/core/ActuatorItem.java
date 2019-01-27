package com.example.core;


import android.app.Fragment;
import android.support.annotation.LayoutRes;

public interface ActuatorItem {
    Fragment getFragment();
    @LayoutRes int getButton();
}
