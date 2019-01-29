package hr.foi.air1817.botanico.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.core.NavigationItem;
import hr.foi.air1817.botanico.PlantRoomDatabase;
import hr.foi.air1817.botanico.R;
import hr.foi.air1817.botanico.adapters.PlantsRecyclerViewAdapter;

public class PlantListFragment extends android.app.Fragment implements NavigationItem {

    private int position;

    @Bind(R.id.rv_plants)
    public RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.plant_list_fragment, container, false);
        ButterKnife.bind(this, v);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        //PlantRoomDatabase.getPlantRoomDatabase(getContext()).plantDao().deleteAll();
        //recyclerView.setAdapter(new PlantsRecyclerViewAdapter(getActivity(), MockDataLoader.getDemoData(PlantRoomDatabase.getPlantRoomDatabase(getContext()))));
        recyclerView.setAdapter(new PlantsRecyclerViewAdapter(getActivity(), PlantRoomDatabase.getPlantRoomDatabase(getContext()).plantDao().getAllPlants()));
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setAdapter(new PlantsRecyclerViewAdapter(getActivity(), PlantRoomDatabase.getPlantRoomDatabase(getContext()).plantDao().getAllPlants()));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.nav_home);
    }

    @Override
    public String getItemName(Context context) {
        return context.getString(R.string.nav_home);
    }

    @Override
    public Drawable getIcon(Context context) {
        return context.getDrawable(R.drawable.ic_botanico);
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
