package hr.foi.air1817.botanico.entities;

import android.content.Context;
import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air1817.botanico.PlantRoomDatabase;

public class GalleryItem {
    public static ArrayList<Uri>  listUri = new ArrayList<Uri>();

    public static void addItem(Uri uri){
        listUri.add(uri);
    }
    public static ArrayList<Uri> getListaUri() {
        return listUri;
    }


    public static void DohvatiSlike(List<Plant> l, Context context){
        int brojac = 0;
        ArrayList<Integer> biljkaId = new ArrayList<>();
        GalleryItem.listUri.clear();

        for (Plant plants : l) {
            biljkaId.add((plants.getId()));
        }

        for (Integer id: biljkaId) {
            brojac = brojac + PlantRoomDatabase.getPlantRoomDatabase(context).plantDao().findPlantById(id).getImageCounter();
            if(brojac == 0){
                for (int i=1; i<=10; i++){
                    StorageReference loadImage = FirebaseStorage.getInstance().getReference(  id+"/images/"+i+".jpg");

                    loadImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            GalleryItem.addItem(uri);
                        }
                    });
                }
            }else {
                for (int i=1; i<=brojac; i++){
                    StorageReference loadImage = FirebaseStorage.getInstance().getReference(  id+"/images/"+i+".jpg");

                    loadImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            GalleryItem.addItem(uri);
                        }
                    });
                }
            }


        }

    }
}
