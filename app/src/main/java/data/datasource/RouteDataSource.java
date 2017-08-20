package data.datasource;

import data.model.Ruta;

/**
 * Created by miguelangel on 20/8/17.
 */

public interface RouteDataSource {

    void save(Ruta route);
    Ruta get();
}
