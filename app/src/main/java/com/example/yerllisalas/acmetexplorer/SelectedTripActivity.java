package com.example.yerllisalas.acmetexplorer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.yerllisalas.acmetexplorer.entity.Constantes;
import com.example.yerllisalas.acmetexplorer.entity.Trip;

import java.util.List;
import java.util.stream.Collectors;

import es.us.acme.market.R;

public class SelectedTripActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TripAdapter tripAdapter;
    GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_trip);

        recyclerView = findViewById(R.id.recycler_selected);
        List <Trip> trips = Constantes.trips, tripsFiltrados;
        tripsFiltrados = trips.stream().filter(Trip::isSeleccionado).collect(Collectors.toList());

        if (tripsFiltrados.size() == 0) {
            Toast.makeText(this, "No trips selected", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "There is " +
                            tripsFiltrados.size() + " trips selected",
                    Toast.LENGTH_LONG).show();
        }
        tripAdapter = new TripAdapter(tripsFiltrados, this);

        gridLayoutManager = new GridLayoutManager(this, 1);


        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(tripAdapter);

    }

}
