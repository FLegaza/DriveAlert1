package data.datasource;

import java.util.List;

import data.model.Ruta;


public final class RouteCacheDataSource implements RouteDataSource {

    private Ruta cache = null;


    public void save(Ruta route) {
        if (route == null) return;

        try {
            cache = (Ruta) route.clone();
        } catch(CloneNotSupportedException exception) {
            cache = null;
        }
    }

    public Ruta get() {
        try {
            return (Ruta) cache.clone();
        } catch(CloneNotSupportedException exception) {
            return null;
        }
    }

    public List<Ruta> getList() {
        return null;
    }

    public void reset() {
        cache = null;
    }
}
