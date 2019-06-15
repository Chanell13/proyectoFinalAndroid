package com.example.yerllisalas.acmetexplorer.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Trip implements Serializable {
    String lugarSalida, lugarDestino, descripcion, url;
    long fechaSalida, fechaLlegada;
    int precio;
    int idt;
    boolean seleccionado;

    public Trip(String lugarSalida, String lugarDestino, String descripcion, long fechaSalida, long fechaLlegada, int precio, String url, Boolean seleccionado, int idt) {
        this.lugarSalida = lugarSalida;
        this.lugarDestino = lugarDestino;
        this.descripcion = descripcion;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
        this.precio = precio;
        this.url = url;
        this.seleccionado = seleccionado;
        this.idt = idt;
    }
    public Trip() {
    }

    public static List <Trip> generaViajes(int numViajes) {
        List <Trip> trips = new ArrayList <>();
        int min = 75, max = 2050, aleatorio, precio;
        String lugarSalida, lugarDestino, descripcion, url;
        Calendar fechaSalida, fechaLlegada, fechaActual = Calendar.getInstance();
        long fsal, flle;
        boolean seleccionado;

        for (int i = 0; i < numViajes; i++) {
            aleatorio = ThreadLocalRandom.current().nextInt(min, max);

            lugarSalida = Constantes.lugarSalida[aleatorio % Constantes.lugarSalida.length];
            lugarDestino = Constantes.ciudades[aleatorio % Constantes.ciudades.length];
            url = Constantes.dicUrlImagenes.get(Constantes.ciudades[aleatorio % Constantes.ciudades.length]);
            descripcion = "Viaje precioso por " + lugarDestino;
            fechaSalida = (Calendar) fechaActual.clone();
            fechaSalida.add(Calendar.DAY_OF_MONTH, aleatorio % 60);
            fsal = fechaSalida.getTimeInMillis() / 1000;
            fechaLlegada = (Calendar) fechaSalida.clone();
            fechaLlegada.add(Calendar.DAY_OF_MONTH, 3 + aleatorio % 8);
            flle = fechaLlegada.getTimeInMillis() / 1000;
            precio = aleatorio;

            seleccionado = aleatorio % 2 == 0;
            trips.add(new Trip(lugarSalida,
                    lugarDestino, descripcion, fsal,
                    flle, precio, url, seleccionado,
                    i));
        }
        return trips;
    }

    public int getIdt() {
        return idt;
    }

    public String getLugarSalida() {
        return lugarSalida;
    }

    public void setLugarSalida(String lugarSalida) {
        this.lugarSalida = lugarSalida;
    }

    public String getLugarDestino() {
        return lugarDestino;
    }

    public void setLugarDestino(String lugarDestino) {
        this.lugarDestino = lugarDestino;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(long fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public long getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(long fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    @Override
    public String toString() {
        return lugarDestino +
                ", Fecha salida: " + Util.formateaFecha(fechaSalida) +
                ", Fecha llegada: " + Util.formateaFecha(fechaLlegada) +
                ", Precio: " + precio;
    }
}
