package com.example.corunacultural;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;
public class MonumentData    implements Parcelable{
    private int rating;
    private String name;
    private String imageURL;
    private Double latitude;
    private Double longitude;
    public MonumentData(JSONObject json){
        try {
            // Inicialización de las variables miembro con los valores del JSONObject
            this.rating = json.getInt("rating");
            this.name = json.getString("name");
            this.imageURL = json.getString("imageURL");
            this.latitude = json.getDouble("latitude");
            this.longitude = json.getDouble("longitude");
        } catch (JSONException e) {
            // Manejo de excepciones en caso de que ocurra un error al obtener los valores del JSONObject
            e.printStackTrace();
        }
    }
    // Método para obtener el rating del monumento
    public int getRating() {
        return rating;
    }
    // Método para obtener el nombre del monumento
    public String getName() {
        return name;
    }
    // Método para obtener la URL de la imagen del monumento
    public String getImageURL() {
        return imageURL;
    }
    public Double getLatitude() {
        return latitude;
    }
    public Double getLongitude() {
        return longitude;
    }

    protected MonumentData(Parcel in) {
        rating = in.readInt();
        name = in.readString();
        imageURL = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }
    public static final Creator<MonumentData> CREATOR = new Creator<MonumentData>() {
        @Override
        public MonumentData createFromParcel(Parcel in) {
            return new MonumentData(in);
        }
        @Override
        public MonumentData[] newArray(int size) {
            return new MonumentData[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rating);
        dest.writeString(name);
        dest.writeString(imageURL);
    }
}
