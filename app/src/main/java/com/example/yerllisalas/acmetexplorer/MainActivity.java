package com.example.yerllisalas.acmetexplorer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yerllisalas.acmetexplorer.entity.Constantes;
import com.example.yerllisalas.acmetexplorer.entity.Enlace;
import com.example.yerllisalas.acmetexplorer.entity.Map;
import com.example.yerllisalas.acmetexplorer.entity.Perfil;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import es.us.acme.market.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constantes.precioMin = 0;
        Constantes.precioMax = 0;
        Constantes.indexTrip = 0;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        gridView = findViewById(R.id.gridView);
        EnlaceAdapter enlaceAdapter = new EnlaceAdapter(Enlace.generaEnlaces(), this);
        gridView.setAdapter(enlaceAdapter);


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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;
        Intent intent2;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_map) {


            intent = new Intent(this, Map.class);

            startActivity(intent);


            return true;
        }
        if (id == R.id.action_perfil) {


            intent2 = new Intent(this, Perfil.class);

            startActivity(intent2);


            return true;
        }
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_trips) {
            // Handle the camera action
        } else if (id == R.id.nav_favourites) {

        } else if (id == R.id.nav_contact) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class EnlaceAdapter extends BaseAdapter {

        List <Enlace> enlaces;
        Context context;

        public EnlaceAdapter(List <Enlace> enlaces, Context context) {
            this.enlaces = enlaces;
            this.context = context;
        }


        @Override
        public int getCount() {
            return enlaces.size();
        }

        @Override
        public Object getItem(int i) {
            return enlaces.get(i);
        }

        @Override
        public long getItemId(int i) {
            return enlaces.get(i).hashCode();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final Enlace enlace = enlaces.get(i);
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.enlace_item, viewGroup, false);
            }
            CardView cardView = view.findViewById(R.id.cardView);
            ImageView imageView = view.findViewById(R.id.enlace_picture);
            TextView textView = view.findViewById(R.id.enlace_name);

            imageView.setImageResource(enlace.getRecursoImageView());
            textView.setText(enlace.getDescripcion());

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, enlace.getClase()));
                }
            });
            return view;

        }
    }
}
