package logic;

import java.io.UnsupportedEncodingException;


/*
Clase para crear la URL para obtener todas las incidencias de tráfico.
 */
public class GetURLTrafico {


    // Aquí se debe coger los parámetros seleccionados en la aplicación y crear la URL a partir de ellos.

    // Sacar la configuración de incidencias que haya alojados en la base de datos o lo de por defecto si fuese
    // la primera vez y agregarlos a la ruta.

    // Constructor
    public String GetURLTrafico() throws UnsupportedEncodingException {
        String direccion = "http://infocar.dgt.es/etraffic/BuscarElementos?latNS=37.40515&longNS=-5.87751&latSW=37.34376&longSW=-6.06686" +
                "&zoom=13&accion=getElementos&Camaras=true&SensoresTrafico=true&SensoresMeteorologico=true" +
                "&Paneles=true&Radares=true&IncidenciasRETENCION=true&IncidenciasOBRAS=false&IncidenciasMETEOROLOGICA=true" +
                "&IncidenciasPUERTOS=true&IncidenciasOTROS=true&IncidenciasEVENTOS=true&IncidenciasRESTRICCIONES=true" +
                "&niveles=true&caracter=acontecimiento";
        return direccion;
    }

}
