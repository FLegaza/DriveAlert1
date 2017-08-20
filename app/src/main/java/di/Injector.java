package di;

import data.repository.RouteRepository;

/**
 * Created by miguelangel on 20/8/17.
 */

public interface Injector {

    RouteRepository getRouteRepository();

}
