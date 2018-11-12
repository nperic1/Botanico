package hr.foi.air1817.botanico.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.security.AccessControlContext;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.transform.URIResolver;

import hr.foi.air1817.botanico.R;
import hr.foi.air1817.botanico.entities.Plant;
import hr.foi.air1817.botanico.viewholders.PlantViewHolder;

public class PlantsRecyclerViewAdapter extends RecyclerView.Adapter<PlantViewHolder> {

    private Context context;
    private List<Plant> plantItems;

    public PlantsRecyclerViewAdapter(Context context, List<Plant> mPlants) {
        super();
        this.context = context;
        this.plantItems = mPlants;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plant_item, viewGroup, false);
        return new PlantViewHolder(v, this, plantItems);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder plantViewHolder, int i) {
        plantViewHolder.plantName.setText(plantItems.get(i).getName());
        plantViewHolder.plantImagePath.setImageURI(Uri.parse(plantItems.get(i).getImage_path()));
    }

    @Override
    public int getItemCount() {
        return plantItems.size();
    }
}
