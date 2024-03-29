package com.example.corunacultural;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FavouriteSC extends AppCompatActivity {

    private ListAdapter listAdapter;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_sc);

        // Obtener referencia al RecyclerView desde el diseño de la actividad
        RecyclerView recyclerView = findViewById(R.id.recyclerViewFavourite);

        // Crear referencia a la actividad actual
        Activity activity = this;

        // Crear una solicitud JSON utilizando Volley
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                "https://raw.githubusercontent.com/iag0p0mb0/DI/main/resources/monuments_cc.json",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Procesar la respuesta JSON exitosa
                        List<FavouriteData> allMonuments = new ArrayList<>();

                        // Iterar a través de los elementos en el JSON Array
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                // Obtener un objeto AnimalData a partir de cada objeto JSON
                                JSONObject monument = response.getJSONObject(i);
                                FavouriteData monuments = new FavouriteData(monument);
                                allMonuments.add(monuments);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Crear un adaptador personalizado y configurarlo en el RecyclerView
                        FavouriteRecyclerViewAdapter adapter = new FavouriteRecyclerViewAdapter(allMonuments, activity);
                        recyclerView.setAdapter(adapter);

                        // Configurar un LinearLayoutManager para el RecyclerView
                        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                        // Configurar el oyente de clics después de haber establecido el adaptador y el LayoutManager
                        adapter.setOnItemClickListener(new FavouriteRecyclerViewAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                FavouriteData clickedMonument = allMonuments.get(position);
                                // Iniciar la nueva actividad
                                Intent intent = new Intent(FavouriteSC.this, DetailSC.class);
                                // Puedes pasar datos adicionales a la nueva actividad si es necesario
                                // intent.putExtra("clave", valor);
                                intent.putExtra("monument",clickedMonument);
                                startActivity(intent);
                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud y mostrar un mensaje de tostada
                        Toast.makeText(activity, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Crear una cola de solicitudes Volley y agregar la solicitud a la cola
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

        BottomNavigationView bar = findViewById(R.id.bottomNavigation);
        bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item1){
                    Intent intent1 = new Intent(FavouriteSC.this,MainSC.class);
                    startActivity(intent1);
                }
                if (item.getItemId() == R.id.item2){
                    Intent intent2 = new Intent(FavouriteSC.this,BestRatedSC.class);
                    startActivity(intent2);
                }
                if (item.getItemId() == R.id.item4){
                    Intent intent4 = new Intent(FavouriteSC.this, ChangePasswordSC.class);
                    startActivity(intent4);
                }
                return true;
            }
        });
    }
}