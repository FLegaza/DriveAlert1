package di;

import data.datasource.RouteCacheDataSource;
import data.datasource.RouteDataSource;
import data.datasource.RouteLocalDataSource;
import data.repository.RouteRepository;

/**
 * Created by miguelangel on 20/8/17.
 */

public final class InjectorImpl implements Injector {

    private static Injector injector = null;
    public static Injector getInstance() {
        if (injector != null) return injector;

        injector = new InjectorImpl();
        return injector;
    }


    private RouteDataSource routeCacheDatasource = null;
    private RouteDataSource routeLocalDatasource = null;

    private RouteDataSource getRouteCacheDataSource() {
        if (routeCacheDatasource != null) return routeCacheDatasource;
        routeCacheDatasource = new RouteCacheDataSource();
        return routeCacheDatasource;
    }

    private RouteDataSource getRouteLocalDataSource() {
        if (routeLocalDatasource != null) return routeLocalDatasource;
        routeLocalDatasource = new RouteLocalDataSource();
        return routeLocalDatasource;
    }

    @Override
    public RouteRepository getRouteRepository() {
        return new RouteRepository(getRouteLocalDataSource(), getRouteCacheDataSource());
    }
}
