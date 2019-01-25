package hr.foi.air1817.botanico.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.util.List;

import hr.foi.air1817.botanico.entities.GalleryItem;

public class GalleryAdapter extends BaseAdapter {

    Context mContext;
    List<GalleryItem> galleryItems;

    public GalleryAdapter(Context c, List<GalleryItem> items){
        galleryItems=items;
        mContext=c;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

}
