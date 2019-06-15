package com.example.yerllisalas.acmetexplorer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.yerllisalas.acmetexplorer.entity.Constantes;
import com.example.yerllisalas.acmetexplorer.entity.Trip;

import java.util.List;
import java.util.stream.Collectors;

import es.us.acme.market.R;

import static com.example.yerllisalas.acmetexplorer.entity.Constantes.precioMax;
import static com.example.yerllisalas.acmetexplorer.entity.Constantes.precioMin;

public class TripActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Switch switchCol;
    ImageView star;
    TripAdapter tripAdapter;
    GridLayoutManager gridLayoutManager;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        recyclerView = findViewById(R.id.recyclerView);
        switchCol = findViewById(R.id.switch1);
        star = findViewById(R.id.slide_circle);

        //Recuperamos el filtro
        sharedPreferences = getSharedPreferences(Constantes.filtroPreferences, MODE_PRIVATE);
        Log.d("onCreate: ",
                String.format("max: %s min: %s",
                        precioMax,
                        precioMin)
        );
        List <Trip> trips = Constantes.trips, tripsFiltrados = null;

        //Filtramos directamente (no es lo más adecuado)
        tripsFiltrados = comprobacionFiltros(trips);

        if (tripsFiltrados.size() == 0) {
            Toast.makeText(
                    this,
                    "No hay viajes con las condiciones de filtro",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(
                    this,
                    "Hay " + tripsFiltrados.size() + " viajes con las condiciones de filtro",
                    Toast.LENGTH_LONG).show();
        }
        tripAdapter = new TripAdapter(tripsFiltrados, this);

        if (switchCol.isChecked()) {
            gridLayoutManager = new GridLayoutManager(this, 2);

        } else {
            gridLayoutManager = new GridLayoutManager(this, 1);
        }

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(tripAdapter);

    }


    private List <Trip> comprobacionFiltros(List <Trip> trips) {

        List <Trip> tripsFiltrados;
        List <Trip> tripsFiltradosPorPrecio;
        long fechaInicioSharedPref = sharedPreferences.getLong(Constantes.fechaInicio, 0);
        long fechaFinSharedPref = sharedPreferences.getLong(Constantes.fechaFin, 0);

        if (fechaInicioSharedPref != 0 && fechaFinSharedPref == 0) {
            //Solo filtro de fecha de salida
            tripsFiltrados = trips.stream().filter(
                    trip -> trip.getFechaSalida() >= fechaInicioSharedPref
            ).collect(Collectors.toList());

        } else if (fechaInicioSharedPref == 0 && fechaFinSharedPref != 0) {
            //Solo filtro fecha de llegada
            tripsFiltrados = trips.stream().filter(
                    trip -> trip.getFechaSalida() < fechaFinSharedPref
            ).collect(Collectors.toList());

        } else if (fechaInicioSharedPref != 0/* && fechaFinSharedPref != 0*/) {//filtro de salida y llegada
            tripsFiltrados = trips.stream().filter((trip) ->
                    trip.getFechaSalida() >= fechaInicioSharedPref &&
                            trip.getFechaSalida() < fechaFinSharedPref &&
                            trip.getFechaLlegada() > fechaInicioSharedPref &&
                            trip.getFechaLlegada() < fechaFinSharedPref).collect(Collectors.toList());
        } else /*if (fechaFinSharedPref == 0 && fechaInicioSharedPref == 0)*/ {//Sin filtros
            tripsFiltrados = trips;
        }

        if (precioMax == 0 && precioMin != 0) {
            tripsFiltradosPorPrecio = tripsFiltrados.stream().filter((trip) ->
                    trip.getPrecio() >= precioMin).collect(Collectors.toList());
            return tripsFiltradosPorPrecio;
        } else if (precioMax != 0 && precioMin != 0) {
            tripsFiltradosPorPrecio = tripsFiltrados.stream().filter((trip) ->
                    trip.getPrecio() >= precioMin &&
                            trip.getPrecio() <= precioMax).collect(Collectors.toList());
            return tripsFiltradosPorPrecio;
        } else if (precioMax != 0) {
            tripsFiltradosPorPrecio = tripsFiltrados.stream().filter((trip) ->
                    trip.getPrecio() < precioMax).collect(Collectors.toList());
            return tripsFiltradosPorPrecio;
        }

        return tripsFiltrados;
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switch1:
                break;
            default:
                startActivity(new Intent(this, FilterActivity.class));
                finish();
                break;
        }
    }
}
