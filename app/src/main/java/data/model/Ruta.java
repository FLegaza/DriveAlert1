package data.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/*
   CLASE RUTA
   Para guardar el origen, destino, duración y distancia de una ruta,
   una lista de rutas y poder usar esa información en la aplicación.
*/
public class Ruta extends RealmObject implements Cloneable {

    @PrimaryKey
    public String idRuta;

    // Origen y Destino
    public String origen;
    public String destino;
    public LatLng latorigen;
    public LatLng latdestino;

    // Hay dos valores de distancia porque la API de Google devuelve dos valores en el JSON
    public String distanciaStr;
    public int distanciaInt;

    // Hay dos valores de duración porque la API de Google devuelve dos valores en el JSON
    public String duracionStr;
    public int duracionInt;

    public List<LatLng> PuntosRuta;
    public RealmList<Incidencia> IncidenciasRuta;
    public String getOrigen() {
        return origen;
    }
    public String getDestino() {
        return destino;
    }
    public LatLng getLatorigen() {
        return latorigen;
    }
    public LatLng getLatdestino() {
        return latdestino;
    }
    public void setOrigen(String origen) {
        this.origen = origen;
    }
    public void setDestino(String destino) {
        this.destino = destino;
    }
    public void setLatorigen(LatLng latorigen) {
        this.latorigen = latorigen;
    }
    public void setLatdestino(LatLng latdestino) { this.latdestino = latdestino; }

     public Object clone() throws CloneNotSupportedException {
         return super.clone();
     }

 }
