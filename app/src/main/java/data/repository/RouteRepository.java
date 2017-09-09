package data.repository;

import java.util.List;

import data.datasource.RouteDataSource;
import data.model.Ruta;


public class RouteRepository {

    private RouteDataSource local;
    private RouteDataSource cache;

    public RouteRepository(RouteDataSource local, RouteDataSource cache) {
        this.local = local;
        this.cache = cache;
    }

    public void setSelected(Ruta route) {
        cache.save(route);
    }

    public Ruta getSelected() {
        return cache.get();
    }

    public void persistRoute(Ruta route) {
        local.save(route);
    }

    public List<Ruta> getListRoutes() {
        return local.getList();
    }
}
