package di;

import data.datasource.RouteDataSource;

/**
 * Created by miguelangel on 20/8/17.
 */

public interface Injector {
    RouteDataSource getRouteDataSource();
}
