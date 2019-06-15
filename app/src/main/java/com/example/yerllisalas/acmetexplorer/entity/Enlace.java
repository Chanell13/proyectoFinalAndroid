package com.example.yerllisalas.acmetexplorer.entity;

import com.example.yerllisalas.acmetexplorer.SelectedTripActivity;
import com.example.yerllisalas.acmetexplorer.TripActivity;

import java.util.ArrayList;
import java.util.List;

import es.us.acme.market.R;

public class Enlace {
    private String descripcion;
    private int recursoImageView;
    private Class clase;

    public Enlace(String descripcion, int recursoImageView, Class clase) {
        this.descripcion = descripcion;
        this.recursoImageView = recursoImageView;
        this.clase = clase;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getRecursoImageView() {
        return recursoImageView;
    }

    public void setRecursoImageView(int recursoImageView) {
        this.recursoImageView = recursoImageView;
    }

    public Class getClase() {
        return clase;
    }

    public void setClase(Class clase) {
        this.clase = clase;
    }

    public static List<Enlace> generaEnlaces(){
        List<Enlace> enlaces=new ArrayList<>();
        enlaces.add(new Enlace("Available Trips", R.drawable.viajar,TripActivity.class));
        enlaces.add(new Enlace("Selected Trips", R.drawable.objetivo,SelectedTripActivity.class));
        return enlaces;
    }
}
