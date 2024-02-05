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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.corunacultural.databinding.ActivityMapsBinding;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainSC extends AppCompatActivity implements OnMapReadyCallback{
    private DrawerLayout drawerLayout;
    ImageView menu, search;
    LinearLayout nav_profile, nav_favouriteSites, nav_rating, nav_options, nav_about;

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private RequestQueue requestQueue;
    private Location lastKnownLocation;
    private FusedLocationProviderClient fusedLocationClient;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sc);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Creamos una cola de respuesta para gestionar la conexión a Google Maps y así obtener el json con el que calcular la ruta
        requestQueue = Volley.newRequestQueue(this);
        //Esta variable nos permitirá calcular la última ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //Obtiene el SupportMapFragment y notifica cuando el mapa está preparado para usarse
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        menu = findViewById(R.id.menu);
        nav_profile = findViewById(R.id.nav_profile);
        nav_favouriteSites = findViewById(R.id.nav_favouriteSites);
        nav_rating = findViewById(R.id.nav_rating);
        nav_options = findViewById(R.id.nav_options);
        nav_about = findViewById(R.id.nav_about);
        search = findViewById(R.id.search);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            Intent intent = new Intent(this, MainSC.class);
            startActivity(intent);
        }

        //Esto hace que si clickamos en el icono de hamburguesa nos abre el drawerLayout
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });
        //Esto se ejecuta si clickamos en la opción de Perfil que está dentro del DrawerLayout, en ese caso nos redirecciona a la clase correspondiente
        nav_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainSC.this, ProfileSC.class);
            }
        });
        //Esto se ejecuta si clickamos en la opción de Mis sitios favoritos que está dentro del DrawerLayout, en ese caso nos redirecciona a la clase correspondiente
        nav_favouriteSites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainSC.this, FavouritesSitesSC.class);
            }
        });
        //Esto se ejecuta si clickamos en la opción de Mis valoraciones que está dentro del DrawerLayout, en ese caso nos redirecciona a la clase correspondiente
        nav_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainSC.this, RatingSC.class);
            }
        });
        //Esto se ejecuta si clickamos en la opción de Opciones que está dentro del DrawerLayout, en ese caso nos redirecciona a la clase correspondiente
        nav_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainSC.this, OptionsSC.class);
            }
        });
        //Esto se ejecuta si clickamos en la opción de Acerca de que está dentro del DrawerLayout, en ese caso nos redirecciona a la clase correspondiente
        nav_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainSC.this, AboutSC.class);
            }
        });

        BottomNavigationView bar = findViewById(R.id.bottomNavigation);
        bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.Churchs){
                    Intent intent1 = new Intent(MainSC.this,MainSC.class);
                    startActivity(intent1);
                }
                if (item.getItemId() == R.id.Monuments){
                    Intent intent2 = new Intent(MainSC.this,MainSC.class);
                    startActivity(intent2);
                }
                if (item.getItemId() == R.id.Museums){
                    Intent intent3 = new Intent(MainSC.this,MainSC.class);
                    startActivity(intent3);
                }
                if (item.getItemId() == R.id.Squares){
                    Intent intent4 = new Intent(MainSC.this, MainSC .class);
                    startActivity(intent4);
                }
                return true;
            }
        });
        // Manejo del clic en el ImageView de búsqueda
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchDialog();
            }
        });
    }
    //Creamos la función de apertura de DrawerLayout
    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
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
    //Creamos la función que nos moverá entre las clases
    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
    @Override
    protected void onPause(){
        super.onPause();
        onBackPressed();
    }
    // Función para mostrar el cuadro de diálogo de búsqueda personalizado
    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Search")
                .setMessage("Enter your search query:")
                .setView(R.layout.dialog_search)
                .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Aquí puedes manejar la búsqueda, por ejemplo, obtener el texto del EditText
                        // EditText editTextSearch = ((AlertDialog) dialog).findViewById(R.id.editTextSearch);
                        // String searchQuery = editTextSearch.getText().toString();
                        Toast.makeText(MainSC.this, "Search button clicked", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;

        LatLng orzan = new LatLng(43.3699, -8.40618);
        mMap.addMarker(new MarkerOptions().position(orzan).title("Playa de Orzán"));

        //Agregamos botones para alejar y acercar el mapa
        Button zoomInButton = findViewById(R.id.zoom_in);
        Button zoomOutButton = findViewById(R.id.zoom_out);

        zoomInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });
        zoomOutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });

        //Añadimos un marcador en Coruña y movemos la cámara
        LatLng coruña = new LatLng(43.3623, -8.4115);
        mMap.addMarker(new MarkerOptions().position(coruña).title("Marker in Coruña"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coruña));

        //Comprobamos que los permisos de ubicación están concedidos
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            return;
        }
        mMap.setMyLocationEnabled(true);

        //Obtener la última ubicación conocida
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if(location != null){
                        lastKnownLocation = location;
                        Toast.makeText(this, location.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        //Si hacemos un longClick, calcula la ruta a ese punto desde la ubicación actual
        mMap.setOnMapLongClickListener(latLng -> {
            Marker newMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Marcador Personalizado"));
            calculateRoute(lastKnownLocation, latLng);
        });

        //Al hacer click en un marcador nos calcula la ruta desde la ubicación actual
        mMap.setOnMarkerClickListener(marker -> {
            LatLng destination = marker.getPosition();
            calculateRoute(lastKnownLocation, destination);
            return true;
        });
    }
    private String getDirectionsUrl(LatLng origin, LatLng destination, String apiKey){
        String str_origin = "origin= " + origin.latitude + "," + origin.longitude;
        String str_destination = "destination= " + destination.latitude + "," + destination.longitude;
        String sensor = "sensor=false";
        String mode = "mode=walking";

        String url = "https://maps.googleapis.com/maps/api/directions/json?" +  str_origin + "&" + str_destination + "&" + sensor + "&" + mode + "&key=" + apiKey;

        return url;
    }
    private void drawRoute(JSONObject response){
        try{
            Toast.makeText(this, "Dibuja ruta", Toast.LENGTH_SHORT).show();
            //Obtiene la ruta principal
            JSONObject route = response.getJSONArray("routes").getJSONObject(0);
            JSONObject overviewPolyline = route.getJSONObject("overview_polyline");
            String encodedPath = overviewPolyline.getString("points");
            List<LatLng> path = PolyUtil.decode(encodedPath);

            //Dibuja la ruta en el mapa
            mMap.addPolyline(new PolylineOptions().addAll(path));
        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(this,e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }
    private void calculateRoute(Location origin, LatLng destination){
        if(origin == null){
            Toast.makeText(this, "No hay origen", Toast.LENGTH_SHORT).show();
            return;
        }
        LatLng originLatLng = new LatLng(origin.getLatitude(), origin.getLongitude());
        String apiKey = "AIzaSyDepnk_Vlv1_90T6MM_wPQ9LsqzQ1nKZtI";
        String url = getDirectionsUrl(originLatLng, destination, apiKey);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    //Aquí se procesa la respuesta JSON
                    drawRoute(response);
                }, error -> {
            error.printStackTrace();
        });
        requestQueue.add(request);
    }
}