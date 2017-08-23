package data.datasource;

import java.util.ArrayList;
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
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();                       // Se podría hacer asíncrono, pero en este caso no hace falta,
        realm.copyToRealm(route);     // ya que no se van a guardar dos rutas a la vez o por otro lado.
        realm.commitTransaction();

        realm.close();
    }

    @Override
    public Ruta get() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Ruta> query = realm.where(Ruta.class);
        return query.findAll().last();
    }

    @Override
    public List<Ruta> getList() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Ruta> query = realm.where(Ruta.class);

        ArrayList<Ruta> routes = new ArrayList<>();
        for (Ruta ruta:query.findAll()) {
            routes.add(ruta);
        }

        return routes;
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
