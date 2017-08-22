package data.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.UUID;

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
    public String idRuta = UUID.randomUUID().toString();

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

    public List<LatLng> PuntosRuta; // Cambiar también por RealmList???
    public RealmList<Incidencia> IncidenciasRuta;

    // Getter - Setter
    public String getIdRuta(){ return idRuta; }
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
    public List<LatLng> getPuntosRuta() { return PuntosRuta; }
    public RealmList<Incidencia> getIncidenciasRuta() { return IncidenciasRuta; }

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
    public void setPuntosRuta(List<LatLng> puntosRuta) { this.PuntosRuta = puntosRuta; }
    public void setIncidenciasRuta(RealmList<Incidencia> incidenciasRuta) { this.IncidenciasRuta = incidenciasRuta; }

     public Object clone() throws CloneNotSupportedException {
         return super.clone();
     }

 }
