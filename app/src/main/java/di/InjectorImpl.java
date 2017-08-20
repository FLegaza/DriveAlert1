package di;

import data.datasource.RouteCacheDataSource;
import data.datasource.RouteDataSource;

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


    private RouteDataSource routeDatasource = null;

    public RouteDataSource getRouteDataSource() {
        if (routeDatasource != null) return routeDatasource;
        routeDatasource = new RouteCacheDataSource();
        return routeDatasource;
    }
}
