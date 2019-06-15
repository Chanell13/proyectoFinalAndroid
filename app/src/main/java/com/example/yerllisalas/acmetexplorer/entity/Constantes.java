package com.example.yerllisalas.acmetexplorer.entity;

import java.util.Hashtable;
import java.util.List;

public final class Constantes {
    public final static String[] ciudades = {
            "Mexico", "Australia", "Mexico",
            "Australia", "Mexico", "Australia",
            "Mexico", "Australia", "Mexico",
            "Australia", "Mexico", "Australia",
            "Mexico", "Australia", "Mexico",
            "Australia"
//            , "United States", "Germany", "Spain", "Turkey", "Argentina",
////            "Brazil", "Canada", "France"
    };
    public final static Hashtable <String, String> dicUrlImagenes = new Hashtable <String, String>() {{
        put("Mexico", "https://s3.amazonaws.com/peoplepng/wp-content/" +
                "uploads/2018/05/18044106/World-Travel-PNG-Download-1024x939.png");
        put("Australia", "http://www.pngall.com/wp-content/" +
                "uploads/2/Travel-PNG-High-Quality-Image.png");
    }};
    public final static String[] lugarSalida =
            {"New York", "Mexico", "Seville", "Barcelona",
                    "Madrid", "Valencia"};
    public final static String
            filtroPreferences = "Filter",
            fechaInicio = "FechaInicio",
            fechaFin = "FechaFin";

    public static final String IntentViaje = "Trip";
    public static int indexTrip = 0;
    public static List <Trip> trips;
    public static int precioMax = 0;
    public static int precioMin = 0;
}
