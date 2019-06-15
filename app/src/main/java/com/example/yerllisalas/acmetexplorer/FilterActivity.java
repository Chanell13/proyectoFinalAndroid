package com.example.yerllisalas.acmetexplorer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yerllisalas.acmetexplorer.entity.Constantes;
import com.example.yerllisalas.acmetexplorer.entity.Util;

import java.util.Calendar;

import es.us.acme.market.R;

import static com.example.yerllisalas.acmetexplorer.entity.Constantes.precioMax;

public class FilterActivity extends AppCompatActivity {


    Calendar calendar = Calendar.getInstance();
    TextView fechaInicio, fechaFin;
    Button fltButton;
    EditText minEditText;
    EditText maxEditText;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        applyFilter();
    }

    private void applyFilter() {
        try {
            Constantes.precioMin = Integer.parseInt(minEditText.getText().toString());
            precioMax = Integer.parseInt(maxEditText.getText().toString());

            if ((precioMax < Constantes.precioMin) && precioMax != 0) {
                Log.d("oncApplyFilter: ",
                        String.format("max: %s min: %s",
                                precioMax,
                                Constantes.precioMin)
                );
                Toast.makeText(this,
                        "Invalid Input Price.\n Filter not applied",
                        Toast.LENGTH_LONG).show();

                Constantes.precioMin = 0;
                precioMax = 0;
            }

            Log.d("oncApplyFilter: ",
                    String.format("max: %s min: %s",
                            precioMax,
                            Constantes.precioMin)
            );
        } catch (NumberFormatException nfe) {
            Toast.makeText(FilterActivity.this,
                    "Some empty field",
                    Toast.LENGTH_SHORT).show();
        }
        startActivity(new Intent(this, TripActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        fltButton = findViewById(R.id.applyFilter);
        minEditText = findViewById(R.id.inputPrecioMin);
        maxEditText = findViewById(R.id.inputPrecioMax);

        fltButton.setOnClickListener(v -> applyFilter());

        sharedPreferences = getSharedPreferences(Constantes.filtroPreferences, MODE_PRIVATE);
        editor = sharedPreferences.edit();


        fechaInicio = findViewById(R.id.textViewFechaInicio);
        fechaFin = findViewById(R.id.textViewFechaFin);
        long fechaInicioSharedPref = sharedPreferences.getLong(Constantes.fechaInicio, 0);
        long fechaFinSharedPref = sharedPreferences.getLong(Constantes.fechaFin, 0);
        if (fechaInicioSharedPref != 0) {
            fechaInicio.setText(Util.formateaFecha(fechaInicioSharedPref));
        }
        if (fechaFinSharedPref != 0) {
            fechaFin.setText(Util.formateaFecha(fechaFinSharedPref));
        }

        minEditText.setText(String.valueOf(Constantes.precioMin));
        maxEditText.setText(String.valueOf(precioMax));
    }

    public void fecha(View view) {
        switch (view.getId()) {
            case R.id.calendarInicio:
                asignaFecha(Constantes.fechaInicio, fechaInicio);
                break;
            case R.id.calendarFin:
                asignaFecha(Constantes.fechaFin, fechaFin);
                break;

        }
    }

    private void asignaFecha(final String fecha, final TextView textView) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (datePicker, yy, mm, dd) -> {
                    calendar.set(yy, mm, dd, 0, 0, 0);
                    String fechaFormateada = Util.formateaFecha(calendar);
                    textView.setText(fechaFormateada);

                    Log.d("fecha: ", String.valueOf(Util.formateaFecha(calendar)));
                    Log.d("fecha: ", String.valueOf(Util.Calendar2long(calendar)));

                    editor.putLong(fecha, Util.Calendar2long(calendar)).commit();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();


    }
}
