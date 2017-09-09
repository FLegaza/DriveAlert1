package data.model;

import io.realm.RealmObject;


public class LatLngRealm extends RealmObject {
    double latitude;
    double longitude;

    public LatLngRealm() {}
    LatLngRealm(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
