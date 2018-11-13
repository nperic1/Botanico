package hr.foi.air1817.botanico.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hr.foi.air1817.botanico.R;
import hr.foi.air1817.botanico.adapters.PlantsRecyclerViewAdapter;
import hr.foi.air1817.botanico.entities.Plant;

public class PlantViewHolder extends RecyclerView.ViewHolder {
    public PlantsRecyclerViewAdapter adapter;

    @Bind(R.id.pv_name)
    public TextView plantName;
    @Bind(R.id.pv_image)
    public ImageView plantImage;

    private Context context;
    private List<Plant> mPlants;

    public PlantViewHolder(final View itemView, PlantsRecyclerViewAdapter adapter, List<Plant> mPlants) {
        super(itemView);

        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();

        this.adapter = adapter;
        this.mPlants = mPlants;
    }
}
