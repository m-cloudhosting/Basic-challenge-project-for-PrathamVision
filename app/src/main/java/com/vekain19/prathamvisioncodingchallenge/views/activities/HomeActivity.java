package com.vekain19.prathamvisioncodingchallenge.views.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.vekain19.prathamvisioncodingchallenge.R;
import com.vekain19.prathamvisioncodingchallenge.views.fragments.SeriviceUserFragment;

import java.text.MessageFormat;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private CoordinatorLayout snackBarView;
    private SharedPreferences pref;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationView = findViewById(R.id.nav_view);

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        if (pref.getString("first_run", "0").equalsIgnoreCase("0")) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("first_run", "1");
            editor.apply();

            setNavigationViewData(
                    pref.getString("first_name_value","temp1"),
                    pref.getString("user_id_value","temp2"));
        }
        else {
                setNavigationViewData(
                        pref.getString("first_name_value","User"),
                        pref.getString("user_id_value"," "));
            }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        snackBarView = findViewById(R.id.snackview);
        Snackbar.make(snackBarView,"Welcome "+pref.getString("first_name_value","temp1")+" !!",Snackbar.LENGTH_SHORT).show();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                HomeActivity.this,
                drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left
        );
        transaction.replace(R.id.homeParent, SeriviceUserFragment.newInstance());
        transaction.commit();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setNavigationViewData(String username, String phoneNumber) {
        View header = navigationView.getHeaderView(0);
        TextView name = header.findViewById(R.id.userName);
        TextView email = header.findViewById(R.id.contactNumber);
        name.setText(MessageFormat.format("{0}", username));
        email.setText(MessageFormat.format("{0}", phoneNumber));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
        } else if (id == R.id.user_profile) {

        } else if (id == R.id.nav_manage) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("first_run", "0");
            editor.apply();

            Intent intent = new Intent(HomeActivity.this,UserAuthentication.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
