package logic;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import data.Param;

import static com.project.francisco.drivealert.R.string.direction_url_api;
import static com.project.francisco.drivealert.R.string.google_maps_api_key;

/*
Clase para recibir y crear la URL a partir de la cual se van a coger los datos tanto para la ruta
como para recoger las incidencias de tráfico.
 */
public class GetURLDirection {

    private String origen;
    private String destino;
    private String stringParam = "";

    // Constructor (Se le pasa el origen, destino y parámetros)
    public String GetURLDirection (String origenSelect, String destinoSelect, Param par) throws UnsupportedEncodingException {
        this.origen = URLEncoder.encode(origenSelect, "utf-8");
        this.destino = URLEncoder.encode(destinoSelect, "utf-8");

        // Sacar parámetros
        if (par.isRutacoche()){
            stringParam = stringParam + "&mode=driving";
        } else if (par.isRutabici()){
            stringParam = stringParam + "&mode=bicycling";
        } else if (par.isRutapie()) {
            stringParam = stringParam + "&mode=walking";
        } else if (par.isRutapublic()){
            stringParam = stringParam + "&mode=transit";
            if (par.isPublicbus()){
                stringParam = stringParam + "&transit_mode=bus";
            } else if (par.isPublictren()){
                stringParam = stringParam + "&transit_mode=train";
            }
        }
        if (par.isPeaje()) stringParam = stringParam + "&avoid=tolls";
        if (par.isAutovia()) stringParam = stringParam + "&avoid=highways";
        if (par.isFerry()) stringParam = stringParam + "&avoid=ferry";


        return direction_url_api + "origin=" + this.origen +
                "&destination=" + this.destino + this.stringParam + "&key=" + google_maps_api_key;
    }

}
