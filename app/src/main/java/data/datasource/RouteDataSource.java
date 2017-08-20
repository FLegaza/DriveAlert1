package data.datasource;

import java.util.List;

import data.model.Ruta;

/**
 * Created by miguelangel on 20/8/17.
 */

public interface RouteDataSource {

    void save(Ruta route);
    Ruta get();
    List<Ruta> getList();
    void reset();
}
