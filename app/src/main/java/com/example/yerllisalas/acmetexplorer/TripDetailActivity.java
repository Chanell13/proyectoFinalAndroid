package com.example.yerllisalas.acmetexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yerllisalas.acmetexplorer.entity.Constantes;
import com.example.yerllisalas.acmetexplorer.entity.Trip;
import com.squareup.picasso.Picasso;

import es.us.acme.market.R;

public class TripDetailActivity extends AppCompatActivity {

    ImageView imagen, estrella;
    TextView ciudad, precio, fechaSalida, fechaLlegada, ciudadSalida;
    Trip trip;
    private String idView;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,
                idView.equals("SelectedTripActivity")
                        ? SelectedTripActivity.class
                        : TripActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        );
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            idView = bundle.getString("idView", "N/A");
            Log.d("onCreate: ", "start " + idView);
        }

        trip = (Trip) getIntent().getSerializableExtra(Constantes.IntentViaje);
        imagen = findViewById(R.id.tripDetailImageViewViaje);
        ciudad = findViewById(R.id.tripDetailTextViewDestino);
        precio = findViewById(R.id.tripDetailTextViewPrecio);
        fechaSalida = findViewById(R.id.tripDetailTextViewFechaSalida);
        //fechaLlegada=findViewById(R.id.tripDetailTextViewFechaLlegada);
        ciudadSalida = findViewById(R.id.tripDetailTextViewLugarSalida);
        estrella = findViewById(R.id.tripSelected);

        if (!trip.getUrl().isEmpty()) {
            Picasso.get()
                    .load(trip.getUrl())
                    .placeholder(android.R.drawable.ic_menu_myplaces)
                    .error(android.R.drawable.ic_menu_myplaces)
                    .into(imagen);

        }

        ciudad.setText(trip.getLugarDestino());
        precio.setText(new String(trip.getPrecio() + "€"));

        if (trip.isSeleccionado()) {
            estrella.setImageResource(android.R.drawable.star_on);
        } else {
            estrella.setImageResource(android.R.drawable.star_off);
        }
    }

    public void seleccionar(View view) {
        if (!trip.isSeleccionado()) {
            trip.setSeleccionado(true);
            estrella.setImageResource(android.R.drawable.star_on);
            Constantes.trips.get(Constantes.indexTrip).setSeleccionado(true);
            Toast.makeText(this, "click 1" + Constantes.indexTrip, Toast.LENGTH_SHORT).show();
        } else {
            trip.setSeleccionado(false);
            Toast.makeText(this, "click 2" + Constantes.indexTrip, Toast.LENGTH_SHORT).show();
            Constantes.trips.get(Constantes.indexTrip).setSeleccionado(false);
            estrella.setImageResource(android.R.drawable.star_off);
        }

    }
}
