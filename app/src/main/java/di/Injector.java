package di;

import data.repository.RouteRepository;


public interface Injector {

    RouteRepository getRouteRepository();

}
