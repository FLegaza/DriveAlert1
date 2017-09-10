package data.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/*
   CLASE RUTA
   Para guardar el origen, destino, duración y distancia de una ruta,
   una lista de rutas y poder usar esa información en la aplicación.
*/
public class Ruta extends RealmObject implements Cloneable {

    public String origen;
    public String destino;
    public LatLngRealm latorigen;
    public LatLngRealm latdestino;

    public String distanciaStr;
    public Integer distanciaInt;

    public String duracionStr;
    public Integer duracionInt;

    public RealmList<LatLngRealm> PuntosRuta;
    public RealmList<Incidencia> IncidenciasRuta;


    // Getter - Setter
    public String getOrigen() {
        return origen;
    }
    public String getDestino() {
        return destino;
    }
    public LatLng getLatorigen() {
        return new LatLng(latorigen.latitude, latdestino.longitude);
    }
    public LatLng getLatdestino() {
        return new LatLng(latdestino.latitude, latdestino.longitude);
    }
    public List<LatLng> getPuntosRuta() {
        ArrayList<LatLng> puntosRuta = new ArrayList<>(PuntosRuta.size());
        for (LatLngRealm punto: PuntosRuta) {
            puntosRuta.add(new LatLng(punto.latitude, punto.longitude));
        }
        return puntosRuta;
    }
    public RealmList<Incidencia> getIncidenciasRuta() { return IncidenciasRuta; }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
    public void setDestino(String destino) {
        this.destino = destino;
    }
    public void setLatorigen(LatLng latorigen) {
        this.latorigen = new LatLngRealm(latorigen.latitude, latorigen.longitude);
    }
    public void setLatdestino(LatLng latdestino) {
        this.latdestino = new LatLngRealm(latdestino.latitude, latdestino.longitude);
    }
    public void setPuntosRuta(List<LatLng> puntosRuta) {
        this.PuntosRuta = new RealmList<>();

        for (LatLng punto: puntosRuta) {
            this.PuntosRuta.add(new LatLngRealm(punto.latitude, punto.longitude));
        }
    }
    public void setPuntosRuta(RealmList<LatLngRealm> puntosRuta) { this.PuntosRuta = puntosRuta; }

    public void setIncidenciasRuta(RealmList<Incidencia> incidenciasRuta) { this.IncidenciasRuta = incidenciasRuta; }


     public Object clone() throws CloneNotSupportedException {
         return super.clone();
     }

 }

