package hr.foi.air1817.botanico;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;
import hr.foi.air1817.botanico.adapters.PlantsRecyclerViewAdapter;
import hr.foi.air1817.botanico.helpers.MockDataLoader;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Bind(R.id.rv_plants)
    public RecyclerView recyclerView;

    private int testInt = 0;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
    private Button probni;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        SetToolbar();
        ManageNavigationMenu();

        LinearLayoutManager llm = new LinearLayoutManager(this); recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(new PlantsRecyclerViewAdapter(getApplicationContext(),MockDataLoader.getDemoData()));



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

    public void OpenGardenView(View view){
        startActivity(new Intent(getApplicationContext(), PlantDetails.class));
    }

    public void openNewGardenActivity(View view) {
        startActivity(new Intent(getApplicationContext(), AddGardenActivity.class));
    }
}
