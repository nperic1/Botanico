package hr.foi.air1817.botanico;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import butterknife.ButterKnife;
import hr.foi.air1817.botanico.entities.GalleryItem;
import hr.foi.air1817.botanico.firebaseMessaging.BotanicoNotificationManager;
import hr.foi.air1817.botanico.fragments.GalleryFragment;
import hr.foi.air1817.botanico.fragments.HelpFragment;
import hr.foi.air1817.botanico.fragments.InfoFragment;
import hr.foi.air1817.botanico.fragments.NotificationsFragment;
import hr.foi.air1817.botanico.fragments.PlantListFragment;


public class MainActivity extends AppCompatActivity implements android.app.FragmentManager.OnBackStackChangedListener, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private android.app.FragmentManager mFm;
    public ArrayList<Uri> uris = new ArrayList<Uri>();
    public ArrayList<Integer> biljkaId= new ArrayList<Integer>();
    public int brojac;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        BotanicoNotificationManager.getInstance(getApplicationContext()).createChannel();

        initializeLayout();
        initializeNavigationManager();

        if(NavigationManager.getInstance().navigationItems.size() == 0){
            NavigationManager.getInstance().addItem(new PlantListFragment(), R.id.dynamic_group);
            NavigationManager.getInstance().addItem(new GalleryFragment(), R.id.dynamic_group);
            NavigationManager.getInstance().addItem(new NotificationsFragment(), R.id.dynamic_group);
            NavigationManager.getInstance().addItem(new InfoFragment(), R.id.static_group);
            NavigationManager.getInstance().addItem(new HelpFragment(), R.id.static_group);
        }

        startMainModule();

        mFm = getFragmentManager();
        mFm.addOnBackStackChangedListener(this);
        GalleryItem.DohvatiSlike(PlantRoomDatabase.getPlantRoomDatabase(getApplicationContext()).plantDao().getAllPlants(), getApplicationContext());



    }

    private void initializeLayout(){
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,  R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        //ovaj listener mora biti ispod inicijalizacije mDrawerToggle!!
        mToolbar.setNavigationOnClickListener(navigationClick);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initializeNavigationManager(){
        NavigationManager nm = NavigationManager.getInstance();
        nm.setDependencies(this, mDrawerLayout, (NavigationView) findViewById(R.id.nav_view));
    }

    private void startMainModule(){
        NavigationManager.getInstance().startMainModule();
    }

    public void openAddGardenActivity(View view) {
        Intent intent = new Intent(this, AddPlantActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() != 0){
            // there is something on the stack, I'm in the fragment
            if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
            else{
                getFragmentManager().popBackStack();
            }
        } else {
            // I'm on the landing page, close the drawer or exit
            if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
            else{
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackStackChanged() {
        mDrawerToggle.setDrawerIndicatorEnabled(mFm.getBackStackEntryCount() == 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(mFm.getBackStackEntryCount() > 0);
        mDrawerToggle.syncState();
    }

    View.OnClickListener navigationClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(getFragmentManager().getBackStackEntryCount() == 0) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
            else{
                onBackPressed();
            }
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            default: NavigationManager.getInstance().selectNavigationItem(menuItem);
                        break;
        }
        return true;
    }
}
