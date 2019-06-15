package com.example.yerllisalas.acmetexplorer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yerllisalas.acmetexplorer.entity.Constantes;
import com.example.yerllisalas.acmetexplorer.entity.Trip;
import com.example.yerllisalas.acmetexplorer.entity.Util;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import es.us.acme.market.R;

public class TripAdapter extends RecyclerView.Adapter <TripAdapter.TripViewHolder> {

    List <Trip> trips;
    Context context;
    LayoutInflater layoutInflater;
    Calendar calendar = Calendar.getInstance();

    public TripAdapter(List <Trip> trips, Context context) {
        this.trips = trips;
        this.context = context;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.from(context).inflate(R.layout.trip_item, viewGroup, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder tripViewHolder, int i) {
        final Trip trip = trips.get(i);

        tripViewHolder.ciudad.setText(trip.getLugarDestino());


        String fechaSalida = Util.formateaFecha(trip.getFechaSalida());
        String fechaLlegada = Util.formateaFecha(trip.getFechaLlegada());
        tripViewHolder.resumen.setText(
                "Salida:" + fechaSalida
                        + "\nLlegada: " + fechaLlegada
                        + "\n" + trip.getPrecio() + "€"
        );

        if (!trip.getUrl().isEmpty()) {
            Picasso.get()
                    .load(trip.getUrl())
                    .placeholder(android.R.drawable.ic_menu_myplaces)
                    .error(android.R.drawable.ic_menu_myplaces)
                    .into(tripViewHolder.imagen);
        }
        if (trip.isSeleccionado()) {
            tripViewHolder.estrella.setImageResource(android.R.drawable.star_on);
        } else {
            tripViewHolder.estrella.setImageResource(android.R.drawable.star_off);
        }


        tripViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TripDetailActivity.class);
                intent.putExtra(Constantes.IntentViaje, trip);
                intent.putExtra("idView",context.getClass().getSimpleName());
                context.startActivity(intent);
                ((Activity) context).finish();
                Constantes.indexTrip = trip.getIdt();
            }
        });
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }


    class TripViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen, estrella;
        TextView ciudad, resumen;
        CardView cardView;

        public TripViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.trip_cardview);
            imagen = view.findViewById(R.id.trip_picture);
            ciudad = view.findViewById(R.id.trip_city);
            resumen = view.findViewById(R.id.trip_summary);
            estrella = view.findViewById(R.id.slide_circle);
        }
    }
}
