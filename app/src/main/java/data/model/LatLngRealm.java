package data.model;

import io.realm.RealmObject;

/**
 * Created by miguelangel on 22/8/17.
 */

public class LatLngRealm extends RealmObject {
    double latitude;
    double longitude;

    public LatLngRealm() {}
    LatLngRealm(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
