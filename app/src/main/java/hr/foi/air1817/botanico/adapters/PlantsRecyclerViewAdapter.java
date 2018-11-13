package hr.foi.air1817.botanico.adapters;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(@NonNull final PlantViewHolder plantViewHolder, int i) {
        plantViewHolder.plantName.setText(plantItems.get(i).getName());
        plantViewHolder.itemView.setTag(plantItems.get(i).getId());

        StorageReference loadImage = FirebaseStorage.getInstance().getReference( plantItems.get(i).getId()+ "/avatar_image/avatar.jpg");

        loadImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){

            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(context.getApplicationContext()).load(uri.toString()).into(plantViewHolder.plantImage);
            }
        });
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
