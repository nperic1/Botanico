package hr.foi.air1817.botanico;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.core.NavigationItem;

import java.util.ArrayList;

public class NavigationManager {

    public ArrayList<NavigationItem> navigationItems;
    private static NavigationManager instance;

    private AppCompatActivity mActivity;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

    private NavigationManager(){
        navigationItems = new ArrayList<NavigationItem>();
    }

    public static NavigationManager getInstance(){
        if(instance == null)
            instance = new NavigationManager();
        return instance;
    }

    public void setDependencies(AppCompatActivity handlerActivity, DrawerLayout drawerLayout, NavigationView navigationView){
        this.mActivity = handlerActivity;
        this.mNavigationView = navigationView;
        this.mDrawerLayout = drawerLayout;
    }

    public void addItem(NavigationItem newItem, int groupId){
        newItem.setPosition(navigationItems.size());
        navigationItems.add(newItem);
        mNavigationView.getMenu()
                .add(groupId,newItem.getPosition(),0,newItem.getItemName(mActivity))
                .setIcon(newItem.getIcon(mActivity))
                .setCheckable(true);
    }

    private void startModule(NavigationItem module) {
        FragmentManager mFragmentManager = mActivity.getFragmentManager();
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, module.getFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    public void startMainModule(){
        NavigationItem mainModule = navigationItems != null ? navigationItems.get(0) : null;
        mNavigationView.getMenu().findItem(mainModule.getPosition()).setChecked(true);
        if (mainModule != null)
            startModule(mainModule);
    }

    public void selectNavigationItem(MenuItem menuItem){
        if (!menuItem.isChecked()) {
            menuItem.setChecked(true);
            mDrawerLayout.closeDrawer(GravityCompat.START);

            NavigationItem selectedItem = navigationItems.get(menuItem.getItemId());
            mActivity.getSupportActionBar().setTitle(selectedItem.getItemName(mActivity));
            startModule(selectedItem);
        }
    }

    public void switchFragment(Fragment fragment, String stackInfo){
        android.app.FragmentTransaction transaction = mActivity.getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(stackInfo);
        transaction.commit();
    }
}
