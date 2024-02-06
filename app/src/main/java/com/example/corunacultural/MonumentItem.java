package com.example.corunacultural;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class MonumentItem implements Parcelable {
    private String itemName;  // Nombre del elemento
    private String imageUrl;   // URL de la imagen del elemento

    // Constructor que recibe un objeto JSON y extrae información para inicializar el objeto MyItem
    public MonumentItem(JSONObject json) {
        try {
            // Obtiene el nombre del elemento del campo "name" en el objeto JSON
            this.itemName = json.getString("name");
            // Obtiene la URL de la imagen del campo "imageURL" en el objeto JSON
            this.imageUrl = json.getString("imageURL");
        } catch (JSONException e) {
            // Maneja la excepción JSONException en caso de errores al analizar el JSON
            e.printStackTrace();
        }
    }

    // Método para obtener el nombre del elemento
    public String getItemName() {
        return itemName;
    }

    // Método para obtener la URL de la imagen del elemento
    public String getImageUrl() {
        return imageUrl;
    }

    // Parcelable
    protected MonumentItem(Parcel in) {
        itemName = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<MonumentItem> CREATOR = new Creator<MonumentItem>() {
        @Override
        public MonumentItem createFromParcel(Parcel in) {
            return new MonumentItem(in);
        }

        @Override
        public MonumentItem[] newArray(int size) {
            return new MonumentItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(imageUrl);
    }
}