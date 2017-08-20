package data.datasource;

import java.util.List;

import data.model.Ruta;

/**
 * Created by miguelangel on 20/8/17.
 */

public class RouteLocalDataSource implements RouteDataSource {

    @Override
    public void save(Ruta route) {
        // TODO save in Database route
    }

    @Override
    public Ruta get() {
        // TODO using local DB get first route
        return null;
    }

    @Override
    public List<Ruta> getList() {
        // TODO using local DB get all routes
        return null;
    }

    @Override
    public void reset() {
        // TODO clear database
    }
}
