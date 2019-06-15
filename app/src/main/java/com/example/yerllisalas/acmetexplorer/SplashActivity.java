package com.example.yerllisalas.acmetexplorer;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.yerllisalas.acmetexplorer.entity.Constantes;
import com.example.yerllisalas.acmetexplorer.entity.Trip;

import es.us.acme.market.R;

public class SplashActivity extends AppCompatActivity {

    private final int DURACION_SPLASH = 4000; // 1 segundo
    private AnimationDrawable animacion;
    private ImageView loading;
    private Animation transicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        loading = findViewById(R.id.loading);
        loading.setBackgroundResource(R.drawable.cargando);
        animacion = (AnimationDrawable) loading.getBackground();
        animacion.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Cuando pasen el tiempo, pasamos a la actividad principal de la aplicación
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            ;
        }, DURACION_SPLASH);
        Constantes.trips = Trip.generaViajes(Constantes.ciudades.length);
    }
}
