package di;

import data.datasource.RouteDataSource;

/**
 * Created by miguelangel on 20/8/17.
 */

interface Injector {
    RouteDataSource getRouteDataSource();
}
