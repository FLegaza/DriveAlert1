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
        if (PuntosRuta == null) return new ArrayList<>();

        ArrayList<LatLng> puntosRuta = new ArrayList<>(PuntosRuta.size());
        for (LatLngRealm punto: PuntosRuta) {
            puntosRuta.add(new LatLng(punto.latitude, punto.longitude));
        }
        return puntosRuta;
    }
    public List<Incidencia> getIncidenciasRuta() {
        if (IncidenciasRuta == null) return new ArrayList<>();

        ArrayList<Incidencia> trafficEvents = new ArrayList<>(IncidenciasRuta.size());
        for (Incidencia trafficEvent: IncidenciasRuta) {
            trafficEvents.add(trafficEvent);
        }
        return trafficEvents;
    }

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

    public void setIncidenciasRuta(List<Incidencia> incidenciasRuta) {
        this.IncidenciasRuta = new RealmList<>();

        for (Incidencia trafficEvent: incidenciasRuta) {
            this.IncidenciasRuta.add(trafficEvent);
        }
    }


     public Object clone() throws CloneNotSupportedException {
         return super.clone();
     }


    // TODO: filter traffic events near to current route
    public List<Incidencia> filterTrafficEvents(List<Incidencia> trafficEvents) {
        List<Incidencia> incidenciasRuta;

        /*// Comparar con las funciones:
        static void distanceBetween(double startLatitude, double startLongitude, double endLatitude, double endLongitude, float[] results)
        // float 	distanceTo(Location dest)
        // (Se devuelve en metros) - Debería ser unos 30Km de cercanía para que se viese*/

        return trafficEvents;
    }

}

