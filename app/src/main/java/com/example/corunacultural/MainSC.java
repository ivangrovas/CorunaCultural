package com.example.corunacultural;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainSC extends AppCompatActivity{

    private DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout nav_profile, nav_favouriteSites, nav_rating, nav_options, nav_about;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sc);

        menu = findViewById(R.id.menu);
        nav_profile = findViewById(R.id.nav_profile);
        nav_favouriteSites = findViewById(R.id.nav_favouriteSites);
        nav_rating = findViewById(R.id.nav_rating);
        nav_options = findViewById(R.id.nav_options);
        nav_about = findViewById(R.id.nav_about);

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
}