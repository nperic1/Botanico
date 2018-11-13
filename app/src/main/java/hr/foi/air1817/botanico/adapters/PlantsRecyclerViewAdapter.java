package hr.foi.air1817.botanico.adapters;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import hr.foi.air1817.botanico.PlantDetailsFragment;
import hr.foi.air1817.botanico.R;
import hr.foi.air1817.botanico.entities.Plant;
import hr.foi.air1817.botanico.viewholders.PlantViewHolder;

public class PlantsRecyclerViewAdapter extends RecyclerView.Adapter<PlantViewHolder> {

    private Activity context;
    private List<Plant> plantItems;

    public PlantsRecyclerViewAdapter(Activity context, List<Plant> mPlants) {
        super();
        this.context = context;
        this.plantItems = mPlants;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plant_item_view, viewGroup, false);
        v.setOnClickListener(onClickListener);

        return new PlantViewHolder(v, this, plantItems);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder plantViewHolder, int i) {
        plantViewHolder.plantName.setText(plantItems.get(i).getName());
        //plantViewHolder.plantImagePath.setImageURI(Uri.parse(plantItems.get(i).getImage_path()));

        plantViewHolder.itemView.setTag(plantItems.get(i).getId());
    }

    @Override
    public int getItemCount() {
        return plantItems.size();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tagData = (int) v.getTag();

            Bundle args = new Bundle();
            args.putInt("id", tagData);

            PlantDetailsFragment pdf = new PlantDetailsFragment();
            pdf.setArguments(args);

            android.app.FragmentTransaction fm = context.getFragmentManager().beginTransaction();
            fm.replace(R.id.fragment_container, pdf);
            fm.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fm.addToBackStack("details");
            fm.commit();
        }
    };
}
