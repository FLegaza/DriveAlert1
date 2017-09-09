package data.datasource;

import java.util.List;

import data.model.Ruta;


public interface RouteDataSource {

    void save(Ruta route);
    Ruta get();
    List<Ruta> getList();
    void reset();
}
