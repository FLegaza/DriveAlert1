package view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.project.francisco.drivealert.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import data.model.Incidencia;
import data.repository.RouteRepository;
import di.library.BaseActivity;
import io.realm.Realm;
import logic.DirectionFinder;
import logic.DirectionFinderListener;
import data.model.Ruta;
import logic.UpdateRoutesWithTrafficEvents;
import logic.UpdateTrafficEvents;

public class MostrarRuta extends BaseActivity implements OnMapReadyCallback, DirectionFinderListener, UpdateTrafficEvents {

    public TextView tvOrigShow;
    public TextView tvDestShow;
    public TextView tvDistance;
    public TextView tvDuration;
    public ImageView ivDistance;
    public ImageView ivDuration;
    public ImageView ivGo;
    public SupportMapFragment map;
    public GoogleMap mMap;
    public TextView tvDescripcion;
    public Button btObtenerIncidencias;

    private List<Marker> origenMarkers = new ArrayList<>();
    private List<Marker> destinoMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog pDEspera;

    private List<Ruta> rutas;
    private RouteRepository repository = injector.getRouteRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_route);
        Realm.init(this);

        injectViews();
        configureShowRoute();
    }

    protected void injectViews() {
        map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        tvOrigShow = (TextView) findViewById(R.id.tvOrigShow);
        tvDestShow = (TextView) findViewById(R.id.tvDestShow);
        tvDistance = (TextView) findViewById(R.id.tvDistance);
        tvDuration = (TextView) findViewById(R.id.tvDuration);
        ivDistance = (ImageView) findViewById(R.id.ivDistance);
        ivDuration = (ImageView) findViewById(R.id.ivDuration);
        ivGo = (ImageView) findViewById(R.id.ivGo);
        tvDescripcion = (TextView) findViewById(R.id.tvDescripcionInci);
        btObtenerIncidencias = (Button) findViewById(R.id.btObtenerIncidencias);
    }

    protected void configureShowRoute() {

        setUpMapIfNeeded();
        loadDirectionFromRoute();

        btObtenerIncidencias.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Ejecutar el algoritmo de las incidencias
                loadTrafficFromRoute(); // Carga la URL y ejecuta el algoritmo de las incidencias.
                // Una vez que tengo una List<Incidencia> completa con todas las incidencias del pais,
                // debo comparar con los PuntosRuta (List<LatLng>) de la ruta y rellenar la List<Incidencia>
                // de esta con las incidencias de 50km alrededor.
            }
        });
    }

    private void setUpMapIfNeeded() {
        if (mMap != null) return;
        map.getMapAsync(this);
    }

    protected void loadDirectionFromRoute() {
        Ruta selectedRoute = repository.getSelected();
        tvOrigShow.setText(selectedRoute.origen);
        tvDestShow.setText(selectedRoute.destino);
        try {
            String directionURL = getURLDirectionFrom(selectedRoute);
            new DirectionFinder(this, directionURL).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    protected void loadTrafficFromRoute() {
        try {
            String trafficURL = getURLTrafficFrom();
            new UpdateRoutesWithTrafficEvents(this, rutas, trafficURL).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    protected String getURLDirectionFrom(Ruta route) {
        String originEncode = null;
        String destinationEncode  = null;
        SharedPreferences UserConfiguration = getSharedPreferences("UserConfiguration", Context.MODE_PRIVATE);
        String car = UserConfiguration.getBoolean("OpcionCar",true) ? "&mode=driving" : "";
        String bici = UserConfiguration.getBoolean("OpcionBici",true) ? "&mode=bicycling" : "";
        String pie = UserConfiguration.getBoolean("OpcionPie",true) ? "&mode=walking" : "";
        String bus = UserConfiguration.getBoolean("OpcionBus",true) ? "&mode=transit&transit_mode=bus&transit_routing_preference=fewer_transfers" : "";
        String tren = UserConfiguration.getBoolean("OpcionTren",true)? "&mode=transit&transit_mode=train&transit_routing_preference=fewer_transfers" : "";
        String peaje = UserConfiguration.getBoolean("Peaje",true) ? "&avoid=tolls" : "";
        String autovia = UserConfiguration.getBoolean("Autovia",true) ? "&avoid=highways" : "";
        String ferry = UserConfiguration.getBoolean("Ferry",true) ? "&avoid=ferry" : "";

        try {
            originEncode = URLEncoder.encode(route.origen, "utf-8");
            destinationEncode = URLEncoder.encode(route.destino, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

       return  getString(R.string.direction_url_api)
                + "origin=" + originEncode
                + "&destination=" + destinationEncode
                + car + bici + pie + bus + tren + peaje + autovia + ferry
                + "&key=" + getString(R.string.google_maps_api_key);
    }

    protected String getURLTrafficFrom() throws UnsupportedEncodingException {
        SharedPreferences UserConfiguration = getSharedPreferences("UserConfiguration", Context.MODE_PRIVATE);
        String Camaras = UserConfiguration.getBoolean("Camaras",true) ? "true" : "false";
        String Sensores = UserConfiguration.getBoolean("Sensores",true) ? "true" : "false";
        String Radares = UserConfiguration.getBoolean("Radares",true) ? "true" : "false";
        String Retenciones = UserConfiguration.getBoolean("Retenciones",true) ? "true" : "false";
        String Obras = UserConfiguration.getBoolean("Obras",true) ? "true" : "false";

        return "http://infocar.dgt.es/etraffic/BuscarElementos?latNS=37.40515&longNS=-5.87751&latSW=37.34376&longSW=-6.06686" +
                "&zoom=13&accion=getElementos" +
                "&Camaras="+ Camaras +
                "&SensoresTrafico=" + Sensores +
                "&SensoresMeteorologico=true" +
                "&Paneles=true" +
                "&Radares=" + Radares +
                "&IncidenciasRETENCION=" + Retenciones +
                "&IncidenciasOBRAS=" + Obras +
                "&IncidenciasMETEOROLOGICA=true" +
                "&IncidenciasPUERTOS=true" +
                "&IncidenciasOTROS=true" +
                "&IncidenciasEVENTOS=true" +
                "&IncidenciasRESTRICCIONES=true" +
                "&niveles=true" +
                "&caracter=acontecimiento";

    }

    protected void updateGoogleMap() {
        if (mMap == null || this.rutas == null) return;

        polylinePaths  = new ArrayList<>();
        origenMarkers  = new ArrayList<>();
        destinoMarkers = new ArrayList<>();

        for (Ruta ruta : this.rutas) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ruta.getLatorigen(), 10));
            ((TextView) findViewById(R.id.tvDuration)).setText(ruta.duracionStr); //ruta.dur.text
            ((TextView) findViewById(R.id.tvDistance)).setText(ruta.distanciaStr); // ruta.dist.text

            origenMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(ruta.origen)
                    .position(ruta.getLatorigen())));
            destinoMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(ruta.destino)
                    .position(ruta.getLatdestino())));
            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < ruta.PuntosRuta.size(); i++)
                polylineOptions.add(ruta.getPuntosRuta().get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

    private void updateDatasource(List<Ruta> routes) {
        Ruta lastRoute = null;

        for (Ruta route : routes) {
            repository.persistRoute(route);
            lastRoute = route;
        }
        repository.setSelected(lastRoute);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng grn = new LatLng(37.1793, -3.5995);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(grn, 10));
        //origenMarkers.add(mMap.addMarker(new MarkerOptions().title("Granada").position(grn)));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            //
            // ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            // public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        updateGoogleMap();

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // Mostrar la incidencia
                // Sacar Datos de la incidencia (Población - Provincia - Causa - Descripción(String en HTML, convertir)
                String provincia = "";
                String poblacion = "";
                String causa = "";
                String descripcion = "";
                // String provincia = ¿¿Ruta.get().List<Incidencia>[i].getPoblación() ...??
                // ¿¿Ruta.get().List<Indidencia>[i].getProvincia() ...??
                // Puede hacerse con un toast o en TextView que ya hay en el layout
                Toast.makeText(getApplicationContext(),
                        "Poblacion: " + poblacion + ", Provincia: " + provincia + ", Causa: " + causa + ", Descripción: " + provincia, Toast.LENGTH_SHORT).show();
                // Debería cambiarlos por Strings para los idiomas
                return false;
            }
        });

    }

    @Override
    public void onDirectionFinderStart() {
        pDEspera = ProgressDialog.show(this, "Espere","Calculando ruta...",true);
        if (origenMarkers != null) {
            for (Marker marker : origenMarkers) {
                marker.remove();
            }
        }
        if (destinoMarkers != null) {
            for (Marker marker : destinoMarkers) {
                marker.remove();
            }
        }
        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(final List<Ruta> rutas) {
        this.rutas = rutas;
        pDEspera.dismiss();

        updateGoogleMap();
        updateDatasource(rutas);
    }

    @Override
    public void onUpdateTrafficEventsStart() {
        pDEspera = ProgressDialog.show(this, "Espere","Actualizando las rutas con sus incidencias...",true);
    }

    @Override
    public void onUpdateTrafficEventsSuccess(List<Incidencia> incidences) {
        pDEspera.dismiss();

        if (incidences != null && incidences.size() > 0) {
            updateGoogleMap();
            updateDatasource(rutas);
        } else {
            Toast.makeText(this, "No se han encontrado para estas rutas incidencias.", Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onUpdateTrafficEventsFailure() {
        pDEspera.dismiss();
        Toast.makeText(this, "Debe crear una ruta para poder encontrar incidencias.", Toast.LENGTH_LONG);
    }
}

