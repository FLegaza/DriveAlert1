package logic;

import java.io.UnsupportedEncodingException;

import data.model.Param;

/*
Clase para crear la URL para obtener todas las incidencias de tráfico.
 */
public class GetURLTrafico {

    private String varCam;
    private String varSensor;
    private String varReten;
    private String varObra;
    private String varRadar;

    // Constructor
    public String GetURLTrafico(Param par) throws UnsupportedEncodingException {

    if (par.isIncicam()) { varCam = "true"; } else { varCam = "false"; }
    if (par.isIncisensor()) { varSensor = "true"; } else { varSensor = "false"; }
    if (par.isIncireten()) { varReten = "true"; } else { varReten = "false"; }
    if (par.isInciobra()) { varObra = "true"; } else { varObra = "false"; }
    if (par.isInciradar()) { varRadar = "true"; } else { varRadar = "false"; }

    String direccion = "http://infocar.dgt.es/etraffic/BuscarElementos?latNS=37.40515&longNS=-5.87751&latSW=37.34376&longSW=-6.06686" +
            "&zoom=13&accion=getElementos" +
            "&Camaras="+ this.varCam +
            "&SensoresTrafico=" + this.varSensor +
            "&SensoresMeteorologico=true" +
            "&Paneles=true" +
            "&Radares=" + this.varRadar +
            "&IncidenciasRETENCION=" + this.varReten +
            "&IncidenciasOBRAS=" + this.varObra +
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
