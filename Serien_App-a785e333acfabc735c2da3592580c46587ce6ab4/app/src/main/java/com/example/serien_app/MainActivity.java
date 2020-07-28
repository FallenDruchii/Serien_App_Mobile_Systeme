package com.example.serien_app;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.net.MalformedURLException;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private FrameLayout fragmentContainer;

    public SharedPreferences.Editor sharedPrefsEdit;

    private HomeFragment homeFragment;

    static String exchangeLink = null;
    Boolean NightModeIsOn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isNetworkConnected()){
            Toast.makeText(this, "Überprüfen Sie Ihre Internetverbindung und starten Sie die App erneut!", Toast.LENGTH_LONG).show();
        }

        try {
            Top10Task task = new Top10Task();
            task.execute(new URL("https://www.werstreamt.es/"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentContainer = findViewById(R.id.fragment_container);
        drawer = findViewById(R.id.drawer_layout);

        homeFragment = new HomeFragment();

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        SharedPreferences appSettingPrefs = getSharedPreferences("AppSettingPrefs", 0);
        sharedPrefsEdit = appSettingPrefs.edit();
        NightModeIsOn = appSettingPrefs.getBoolean("NightMode", true);

        if (NightModeIsOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


        if (savedInstanceState == null) {
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }


    private boolean isNetworkConnected() {

        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
        }catch (Exception e){
            e.printStackTrace();
        }return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                if (Listen.getTopList().isEmpty()) {
                    try {
                        Top10Task task = new Top10Task();
                        task.execute(new URL("https://www.werstreamt.es/"));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();

                break;
            case R.id.nav_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchFragment()).commit();
                break;
            case R.id.nav_watchlist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WatchlistFragment().newInstance(Listen.getWatchList())).commit();
                break;
            case R.id.nav_lastsearched:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LastsearchedFragment().newInstance(Listen.getSearchHistory())).commit();
                break;
            case R.id.nav_colormode:
                toggleUIMode();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void toggleUIMode() {
        if (NightModeIsOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            sharedPrefsEdit.putBoolean("NightMode", false);
            sharedPrefsEdit.apply();
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            sharedPrefsEdit.putBoolean("NightMode", true);
            sharedPrefsEdit.apply();
        }
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Drücken Sie zurück erneut um die App zu verlassen.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }

}
