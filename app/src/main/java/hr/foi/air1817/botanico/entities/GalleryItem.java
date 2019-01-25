package hr.foi.air1817.botanico.entities;

public class GalleryItem {
    private String mName;
    private String mItemUrl;

    public GalleryItem(){

    }


    public GalleryItem(String name, String url){
        if (name.trim().equals("")){
            name="No name";
        }
        mName=name;
        mItemUrl=url;
    }

    public String getmItemUrl() {
        return mItemUrl;
    }
}
