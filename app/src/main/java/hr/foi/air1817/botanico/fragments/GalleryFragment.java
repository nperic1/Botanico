package hr.foi.air1817.botanico.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import hr.foi.air1817.botanico.MainActivity;
import hr.foi.air1817.botanico.NavigationItem;
import hr.foi.air1817.botanico.R;
import hr.foi.air1817.botanico.adapters.GalleryAdapter;
import hr.foi.air1817.botanico.entities.GalleryItem;

public class GalleryFragment extends Fragment implements NavigationItem {
    List<GalleryItem> itemList;
    private DatabaseReference mDatabaseRef;
    private GalleryAdapter mAdapter;
    private int position;
    GridView galleryGridView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.gallery_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        galleryGridView= getActivity().findViewById(R.id.gallery_gridview);

        itemList= new ArrayList<GalleryItem>();
        mDatabaseRef=FirebaseDatabase.getInstance().getReference("images");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    GalleryItem galleryItem = postSnapshot.getValue(GalleryItem.class);
                    itemList.add(galleryItem);
                }
                mAdapter= new GalleryAdapter((AppCompatActivity)getActivity(),itemList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               Toast.makeText((AppCompatActivity)getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        galleryGridView.setAdapter(mAdapter);


    }

    @Override
    public String getItemName(Context context) {
        return context.getString(R.string.nav_gallery);
    }

    @Override
    public Drawable getIcon(Context context) {
        return context.getDrawable(R.drawable.ic_image);
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
