package hr.foi.air1817.botanico.entities;

import android.net.Uri;

import java.util.ArrayList;

public class GalleryItem {
    public static ArrayList<Uri>  listUri = new ArrayList<Uri>();

    public static void addItem(Uri uri){
        listUri.add(uri);
    }
    public static ArrayList<Uri> getListaUri() {
        return listUri;
    }

    public static void setListaUri(ArrayList<Uri> listaUri) {
        GalleryItem.listUri = listaUri;
    }
}
