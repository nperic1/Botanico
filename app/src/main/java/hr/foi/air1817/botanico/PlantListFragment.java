package hr.foi.air1817.botanico;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import hr.foi.air1817.botanico.adapters.PlantsRecyclerViewAdapter;
import hr.foi.air1817.botanico.helpers.MockDataLoader;

public class PlantListFragment extends android.app.Fragment {

    @Bind(R.id.rv_plants)
    public RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.plant_list_fragment, container, false);
        ButterKnife.bind(this, v);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(new PlantsRecyclerViewAdapter(getActivity(),MockDataLoader.getDemoData()));

        return v;
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
}
