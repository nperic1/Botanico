package hr.foi.air1817.botanico.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import hr.foi.air1817.botanico.PlantRoomDatabase;
import hr.foi.air1817.botanico.R;
import hr.foi.air1817.botanico.adapters.PlantsRecyclerViewAdapter;
import hr.foi.air1817.botanico.entities.GalleryItem;

public class GalleryImageView extends Fragment {

    int position;
    ImageView imageView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.gallery_image_view_fragment, container, false);



        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = (ImageView) view.findViewById(R.id.gallery_full_size_image);

        final Bundle data = getArguments();
        position = (int) data.get("position");
        Log.d("JJJJJJJJJJJJJJ", String.valueOf(position));
        Picasso.with(getActivity()).load(GalleryItem.getListaUri().get(position)).into(imageView);

    }
}
