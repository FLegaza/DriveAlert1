package logic;

import java.io.UnsupportedEncodingException;

import data.model.Param;

/*
Clase para crear la URL para obtener todas las incidencias de tráfico.
 */
public class GetURLTrafico {

     public String GetURL() throws UnsupportedEncodingException {

        Param par = new Param();

        String varCam = par.isIncicam() ? "True" : "False";
        String varSensor = par.isIncisensor() ? "True" : "False";
        String varReten = par.isIncireten() ? "True" : "False";
        String varObra = par.isInciobra() ? "True" : "False";
        String varRadar = par.isInciradar() ? "True" : "False";

        String direccion = "http://infocar.dgt.es/etraffic/BuscarElementos?latNS=37.40515&longNS=-5.87751&latSW=37.34376&longSW=-6.06686" +
                "&zoom=13&accion=getElementos" +
                "&Camaras="+ varCam +
                "&SensoresTrafico=" + varSensor +
                "&SensoresMeteorologico=true" +
                "&Paneles=true" +
                "&Radares=" + varRadar +
                "&IncidenciasRETENCION=" + varReten +
                "&IncidenciasOBRAS=" + varObra +
                "&IncidenciasMETEOROLOGICA=true" +
                "&IncidenciasPUERTOS=true" +
                "&IncidenciasOTROS=true" +
                "&IncidenciasEVENTOS=true" +
                "&IncidenciasRESTRICCIONES=true" +
                "&niveles=true" +
                "&caracter=acontecimiento";
        return direccion;
    }

}
