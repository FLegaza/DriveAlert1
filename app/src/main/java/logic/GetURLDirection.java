package logic;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.project.francisco.drivealert.R.string.direction_url_api;
import static com.project.francisco.drivealert.R.string.google_maps_api_key;

/*
Clase para recibir y crear la URL a partir de la cual se van a coger los datos tanto para la ruta
como para recoger las incidencias de tráfico.
 */
public class GetURLDirection {

    private String origen;
    private String destino;

    // Constructor
    public String GetURLDirection (String origenSelect, String destinoSelect) throws UnsupportedEncodingException {
        this.origen = URLEncoder.encode(origenSelect, "utf-8");
        this.destino = URLEncoder.encode(destinoSelect, "utf-8");

        // Debería comprobar los parámetros e introducir la URL con ellos.
        // Ir comprobando de la base de datos los parámetros
        // Añadimos los param a la URL

        // Sacar los parámetros que haya alojados en la base de datos o lo de por defecto si fuese
        // la primera vez y agregarlosa la ruta

        return direction_url_api + "origin=" + this.origen +
                "&destination=" + this.destino + "&key=" + google_maps_api_key;
    }

}
