package com.example.devanshi.firebasewithlogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.devanshi.firebasewithlogin.fragement.HomeFragment;
import com.example.devanshi.firebasewithlogin.fragement.MapFragment;
import com.example.devanshi.firebasewithlogin.fragement.RegisterFragment;
import com.example.devanshi.firebasewithlogin.util.Mypref;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.devanshi.firebasewithlogin.util.Mypref.Email;
import static com.example.devanshi.firebasewithlogin.util.Mypref.FLAG;
import static com.example.devanshi.firebasewithlogin.util.Mypref.Name;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    TextView email,name;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        email=navigationView.getHeaderView(0).findViewById(R.id.nav_header_email);
        name=navigationView.getHeaderView(0).findViewById(R.id.nav_header_name);
        image=navigationView.getHeaderView(0).findViewById(R.id.imageView);


        Mypref mypref=new Mypref();
        mypref.SharedPreferenceUtils(HomeActivity.this);
        email.setText(mypref.getStringValue("Email",""));
        name.setText(mypref.getStringValue("Name",""));
       mypref.Glide(mypref.getStringValue("Image",""),HomeActivity.this,image);
        android.support.v4.app.FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container_1,new HomeFragment());
        ft.commit();


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_hospital) {
            android.support.v4.app.FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_1,new RegisterFragment());
            ft.addToBackStack(null);
            ft.commit();
        }  else if (id == R.id.nav_home)
        {
            android.support.v4.app.FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_1,new HomeFragment());
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_logout)
        {
            Mypref mypref=new Mypref();
            mypref.SharedPreferenceUtils(HomeActivity.this);
//            mypref.setValue(Email,"");
//            HomeActivity.this.getSharedPreferences(FLAG, Context.MODE_PRIVATE).edit().clear().commit();
          //  FirebaseAuth.getInstance().signOut();
            mypref.clear();
            Intent i = new Intent(HomeActivity.this,SigninActivity.class);
            startActivity(i);

        }else if(id == R.id.nav_map){
            android.support.v4.app.FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_1,new MapFragment());
            ft.addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
