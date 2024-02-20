package com.example.corunacultural;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.corunacultural.databinding.ActivityMainScBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.maps.android.PolyUtil;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainSC extends AppCompatActivity implements OnMapReadyCallback{
    private DrawerLayout drawerLayout;
    ImageView search;
    private GoogleMap mMap;
    private RequestQueue requestQueue;
    private Location lastKnownLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private NavigationView navigationView;
    private RecyclerView recyclerViewSuggest;
    // Método llamado cuando se crea la actividad
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sc);

        // Configuración de la barra de herramientas
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        // Obtención del DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);

        // Creación de la cola de solicitud para la conexión a Google Maps
        requestQueue = Volley.newRequestQueue(this);
        // Inicialización del cliente de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Obtención del SupportMapFragment y notificación cuando el mapa está listo
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment2);
        if (mapFragment != null) { mapFragment.getMapAsync(this); }

        // Configuración del ActionBarDrawerToggle para la navegación lateral
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Obtención de la NavigationView y configuración del listener para los elementos del menú
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Manejo de los clics en los elementos del menú
                int itemId = item.getItemId();
                if (itemId == R.id.nav_profile) {
                    Intent intent1 = new Intent(MainSC.this, ProfileSC.class);
                    startActivity(intent1);
                }
                if (itemId == R.id.nav_favouriteSites) {
                    Intent intent1 = new Intent(MainSC.this, FavouriteSC.class);
                    startActivity(intent1);
                }
                if (itemId == R.id.nav_rating) {
                    Intent intent1 = new Intent(MainSC.this, ReviewSC.class);
                    startActivity(intent1);
                }
                if (itemId == R.id.nav_options) {
                    Intent intent1 = new Intent(MainSC.this, OptionsSC.class);
                    startActivity(intent1);
                }
                drawerLayout.close(); // Cerrar el DrawerLayout después de hacer clic
                return true;
            }
        });

        // Configuración del listener para los elementos de la barra de navegación inferior
        BottomNavigationView bar = findViewById(R.id.bottomNavigation);
        bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Manejo de los clics en los elementos de la barra de navegación inferior
                if (item.getItemId() == R.id.Churchs) {
                    Intent intent1 = new Intent(MainSC.this, MainSC.class);
                    startActivity(intent1);
                }
                if (item.getItemId() == R.id.Monuments) {
                    Intent intent2 = new Intent(MainSC.this, MainSC.class);
                    startActivity(intent2);
                }
                if (item.getItemId() == R.id.Museums) {
                    Intent intent3 = new Intent(MainSC.this, MainSC.class);
                    startActivity(intent3);
                }
                if (item.getItemId() == R.id.Squares) {
                    Intent intent4 = new Intent(MainSC.this, MainSC.class);
                    startActivity(intent4);
                }
                return true;
            }
        });

        // Configuración del RecyclerView para mostrar monumentos sugeridos
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSuggest = findViewById(R.id.recyclerViewSuggest);
        recyclerViewSuggest.setLayoutManager(layoutManager);

        Activity activity = this;

        // Solicitud de JSON para obtener datos de monumentos desde un servidor remoto
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                "https://raw.githubusercontent.com/iag0p0mb0/DI/main/resources/monuments_cc.json",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Procesar la respuesta JSON exitosa
                        List<MonumentData> allSuggestedMonuments = new ArrayList<>();
                        // Iterar a través de los elementos en el JSONArray
                        for (int i = 0; i < Math.min(response.length(), 25); i++) {
                            try {
                                // Obtener un objeto MonumentData a partir de cada objeto JSON
                                JSONObject monument = response.getJSONObject(i);
                                MonumentData monuments = new MonumentData(monument);
                                allSuggestedMonuments.add(monuments);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        // Crear un adaptador personalizado y configurarlo en el RecyclerView
                        SuggestedMonumentRecyclerViewAdapter adapter = new SuggestedMonumentRecyclerViewAdapter(allSuggestedMonuments, activity);
                        recyclerViewSuggest.setAdapter(adapter);
                        // Configurar el listener de clics después de haber establecido el adaptador y el LayoutManager
                        adapter.setOnItemClickListener(new SuggestedMonumentRecyclerViewAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                MonumentData clickedMonument = allSuggestedMonuments.get(position);
                                LatLng monumentLocation = new LatLng(clickedMonument.getLatitude(), clickedMonument.getLongitude());
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(monumentLocation, 18f));
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
    }

    //Creamos la función que haga que volvamos para atrás si le damos botón de atrás
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    // Método llamado cuando el mapa está listo para ser utilizado
    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;

        // Añadir un marcador en A Coruña y mover la cámara
        LatLng coruna = new LatLng(43.3623, -8.4115);
        mMap.addMarker(new MarkerOptions().position(coruna).title("A Coruña"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coruna, 13.5f));

        // Añadir marcadores predefinidos
        LatLng plzMariaPita = new LatLng(43.3710452, -8.396409);
        mMap.addMarker(new MarkerOptions().position(plzMariaPita).title("Plaza de María Pita"));

        LatLng torreHercules = new LatLng(43.385918000, -8.406607000);
        mMap.addMarker(new MarkerOptions().position(torreHercules).title("Torre de Hércules"));

        LatLng millenium = new LatLng(43.37697, -8.42282);
        mMap.addMarker(new MarkerOptions().position(millenium).title("Millenium"));

        LatLng obelisco = new LatLng(43.368326, -8.402548);
        mMap.addMarker(new MarkerOptions().position(obelisco).title("Obelisco"));

        // Comprobar si los permisos de ubicación están concedidos
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            return;
        }
        mMap.setMyLocationEnabled(true);

        // Obtener la última ubicación conocida
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if(location != null){
                        lastKnownLocation = location;
                        Toast.makeText(this, location.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Si se realiza un longClick, calcular la ruta a ese punto desde la ubicación actual
        mMap.setOnMapClickListener(latLng -> {
            Marker newMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Marcador Personalizado"));
            calculateRoute(lastKnownLocation, latLng);
        });

        // Al hacer clic en un marcador, calcular la ruta desde la ubicación actual
        mMap.setOnMarkerClickListener(marker -> {
            LatLng destination = marker.getPosition();
            calculateRoute(lastKnownLocation, destination);
            return true;
        });
    }

    // Obtener la URL de la API para obtener las direcciones
    private String getDirectionsUrl(LatLng origin, LatLng destination, String apiKey){
        String str_origin = "origin= " + origin.latitude + "," + origin.longitude;
        String str_destination = "destination= " + destination.latitude + "," + destination.longitude;
        String sensor = "sensor=false";
        String mode = "mode=walking";

        String url = "https://maps.googleapis.com/maps/api/directions/json?" +  str_origin + "&" + str_destination + "&" + sensor + "&" + mode + "&key=" + apiKey;

        return url;
    }

    // Dibujar la ruta en el mapa
    private void drawRoute(JSONObject response){
        try{
            Toast.makeText(this, "Dibuja ruta", Toast.LENGTH_SHORT).show();
            // Obtener la ruta principal
            JSONObject route = response.getJSONArray("routes").getJSONObject(0);
            JSONObject overviewPolyline = route.getJSONObject("overview_polyline");
            String encodedPath = overviewPolyline.getString("points");
            List<LatLng> path = PolyUtil.decode(encodedPath);

            // Dibujar la ruta en el mapa
            mMap.addPolyline(new PolylineOptions().addAll(path));
        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(this,e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // Calcular la ruta desde la ubicación actual hasta el destino
    private void calculateRoute(Location origin, LatLng destination){
        if(origin == null){
            Toast.makeText(this, "No hay origen", Toast.LENGTH_SHORT).show();
            return;
        }
        LatLng originLatLng = new LatLng(origin.getLatitude(), origin.getLongitude());
        String apiKey = "AIzaSyDepnk_Vlv1_9OT6MM-wPQ9LsqzQ1nKZtI";
        String url = getDirectionsUrl(originLatLng, destination, apiKey);
        // Crear una solicitud JSON para obtener la ruta desde la API de Google Maps
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    // Procesar la respuesta JSON
                    drawRoute(response);
                }, error -> {
            error.printStackTrace();
        });
        // Agregar la solicitud a la cola de solicitudes
        requestQueue.add(request);
    }
}