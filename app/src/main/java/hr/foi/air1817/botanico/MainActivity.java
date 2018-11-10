package hr.foi.air1817.botanico;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;
import hr.foi.air1817.botanico.adapters.PlantsRecycleViewAdapter;
import hr.foi.air1817.botanico.helpers.MockDataLoader;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Bind(R.id.rv_plants)
    public RecyclerView recyclerView;

    private int testInt = 0;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.BotanicoTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        SetToolbar();
        ManageNavigationMenu();

        LinearLayoutManager llm = new LinearLayoutManager(this); recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(new PlantsRecycleViewAdapter(getApplicationContext(),MockDataLoader.getDemoData()));

        PlantViewModel viewModel = ViewModelProviders.of(this).get(PlantViewModel.class);
        LiveData<DataSnapshot> liveData = viewModel.getDataSnapshotLiveData();

        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    // update the UI here with values in the snapshot
                    Long temp = dataSnapshot.child("temp").getValue(Long.class);
                    Log.d("TEMP", temp.toString());
                    Long humidity = dataSnapshot.child("humidity").getValue(Long.class);
                    Log.d("HUMIDITY", humidity.toString());
                    Long light = dataSnapshot.child("light").getValue(Long.class);
                    Log.d("LIGHT", light.toString());
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void SetToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white);
    }

    public void ManageNavigationMenu(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView;
        navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Kod za update korisnickog sucelja
                        // Npr, promjena pogleda iz Home u Gallery

                        return true;
                    }
                });
    }
}
