package data.datasource;

import java.util.List;

import data.model.Ruta;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by miguelangel on 20/8/17.
 */

public class RouteLocalDataSource implements RouteDataSource {

    @Override
    public void save(Ruta route) {
        // TODO save in Database route
        // El init lo hago en Mostrar Ruta.
        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();
        /*
        Primera opción
         */
        realm.beginTransaction();                       // Se podría hacer asíncrono, pero en este caso no hace falta,
        Ruta realmRoute = realm.copyToRealm(route);     // ya que no se van a guardar dos rutas a la vez o por otro lado.
        realm.commitTransaction();
        /*
        Segunda opción
         */
        realm.beginTransaction();
        Ruta ruta = realm.createObject(Ruta.class);
        ruta.setOrigen(route.origen);
        ruta.setDestino(route.destino);
        ruta.setLatorigen(route.latorigen);
        ruta.setLatdestino(route.latdestino);
        ruta.setPuntosRuta(route.PuntosRuta);
        ruta.setIncidenciasRuta(route.IncidenciasRuta);
        realm.commitTransaction();

        realm.close();
    }

    @Override
    public Ruta get() {
        // TODO using local DB get first route
        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();
        // realm.beginTransaction();

        RealmQuery<Ruta> query = realm.where(Ruta.class);
        RealmResults<Ruta> results = query.findAll();

        // ¿Cómo devuelvo una ruta para el return?? Results devolvería todas, bucle ID?
        // RealmResults are ordered, and you can access the individual objects through an index.. ¿Cómo?

        // realm.commitTransaction();
        realm.close();
        return null;
    }

    @Override
    public List<Ruta> getList() {
        // TODO using local DB get all routes

        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();
        // realm.beginTransaction();
        RealmQuery<Ruta> query = realm.where(Ruta.class);
        RealmResults<Ruta> results = query.findAll();

        // realm.commitTransaction();
        realm.close();

        return results;
    }

    @Override
    public void reset() {

        try {
            Realm.deleteRealm(Realm.getDefaultConfiguration());
            //Realm file has been deleted.
        } catch (Exception ex){
            ex.printStackTrace();
            //No Realm file to remove.
        }
    }
}
