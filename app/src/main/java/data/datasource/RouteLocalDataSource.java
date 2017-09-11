package data.datasource;

import java.util.ArrayList;
import java.util.List;

import data.model.Ruta;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RouteLocalDataSource implements RouteDataSource {

    @Override
    public void save(Ruta route) {
        // TODO: search route IF exist then update values, ELSE persist

        // IF PART
        
        // ELSE PART
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.copyToRealm(route);
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
