package data.datasource;

import data.model.Ruta;

/**
 * Created by miguelangel on 20/8/17.
 */

final class RouteCacheDataSource implements RouteDataSource {

    private Ruta cache = null;


    public void save(Ruta route) {
        cache = route;
    }

    public Ruta get() {
        return cache;
    }
}
