package hr.foi.air1817.botanico.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryGridAdapter extends BaseAdapter {
    private Context mContext;
    public ArrayList<Bitmap> slike;

    public GalleryGridAdapter(Context context, ArrayList<Bitmap> slikice){
       slike = slikice;
        mContext = context;
    }

    @Override
    public int getCount() {
        return slike.size();
    }

    @Override
    public Object getItem(int position) {
        return slike.indexOf(position);
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
            imageView.setLayoutParams(new GridView.LayoutParams(500, 500));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else{
            imageView = (ImageView) convertView;
        }
            imageView.setImageBitmap(slike.get(position));
            return imageView;
    }
}
