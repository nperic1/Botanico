package hr.foi.air1817.botanico.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class GalleryGridAdapter extends BaseAdapter {
    private Context mContext;
    public ArrayList<Uri> images;

    public GalleryGridAdapter(Context context, ArrayList<Uri> imagesUri){
        images = imagesUri;
        mContext = context;

    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.indexOf(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 350));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else{
            imageView = (ImageView) convertView;
        }
        Picasso.with(mContext).load(String.valueOf(images.get(position))).into(imageView);
        return imageView;
    }
}