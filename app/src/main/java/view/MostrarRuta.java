package view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

import data.model.Param;
import data.repository.RouteRepository;
import di.library.BaseActivity;
import io.realm.Realm;
import logic.DirectionFinder;
import logic.DirectionFinderListener;
import data.model.Ruta;
import logic.GetIncidencias;
import logic.GetIncidenciasListener;
import logic.GetURLDirection;
import logic.GetURLTrafico;


public class MostrarRuta extends BaseActivity implements OnMapReadyCallback, DirectionFinderListener {

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

        // Mostrar el botón si en configuración la variable activarIncidencias está a TRUE;

        // De las incidencias que se muestren, estarán guardadas en list<indidencia> en la ruta,
        // por lo que el ID que se pinche en el mapa, mostrar la descripción en el textView, la
        // descripción sería la carretera junto a las descripcion de la incidencia.
        // (La traducción no sé si será posible)


        setUpMapIfNeeded();
        loadDirectionFromRoute();

        btObtenerIncidencias.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Ejecutar el algoritmo de las incidencias
                loadTrafficFromRoute();
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
            // new GetIncidencias(this, trafficURL).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    protected String getURLDirectionFrom(Ruta route) {
        String originEncode = null;
        String destinationEncode  = null;

        try {
            originEncode = URLEncoder.encode(route.origen, "utf-8");
            destinationEncode = URLEncoder.encode(route.destino, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String stringParam = "&mode=driving&avoid=tolls&avoid=highways&avoid=ferry";
        String var =  getString(R.string.direction_url_api)
                + "origin=" + originEncode
                + "&destination=" + destinationEncode
                + stringParam
                + "&key=" + getString(R.string.google_maps_api_key);

        return var;
    }

    protected String getURLTrafficFrom() throws UnsupportedEncodingException {
        Param par = new Param();

        String varCam = par.isIncicam() ? "True" : "False";
        String varSensor = par.isIncisensor() ? "True" : "False";
        String varReten = par.isIncireten() ? "True" : "False";
        String varObra = par.isInciobra() ? "True" : "False";
        String varRadar = par.isInciradar() ? "True" : "False";

        String direccion = "http://infocar.dgt.es/etraffic/BuscarElementos?latNS=37.40515&longNS=-5.87751&latSW=37.34376&longSW=-6.06686" +
                "&zoom=13&accion=getElementos" +
                "&Camaras="+ varCam +
                "&SensoresTrafico=" + varSensor +
                "&SensoresMeteorologico=true" +
                "&Paneles=true" +
                "&Radares=" + varRadar +
                "&IncidenciasRETENCION=" + varReten +
                "&IncidenciasOBRAS=" + varObra +
                "&IncidenciasMETEOROLOGICA=true" +
                "&IncidenciasPUERTOS=true" +
                "&IncidenciasOTROS=true" +
                "&IncidenciasEVENTOS=true" +
                "&IncidenciasRESTRICCIONES=true" +
                "&niveles=true" +
                "&caracter=acontecimiento";

        return direccion;
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

        for (Ruta route:routes) {
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
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        updateGoogleMap();

        // Set a listener for marker click.
        // mMap.setOnMarkerClickListener(this);

    }


    /*
    // Cambiar el onMarkerClick para rellenar el TextView de la incidencia.
    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }
    Eventos de clic de marcadores

    Puedes usar un OnMarkerClickListener para escuchar eventos de clic en el marcador.
    Para configurar este receptor en el mapa, llama a GoogleMap.setOnMarkerClickListener(OnMarkerClickListener).
    Cuando un usuario haga un clic en un marcador, se llamará a onMarkerClick(Marker) y el marcador
     se pasará como un argumento. Este método devuelve un booleano que indica si consumiste el evento
     (es decir, si deseas suprimir el comportamiento predeterminado). Si devuelve un valor false, el
     comportamiento predeterminado se sumará al comportamiento personalizado que elijas.
    El comportamiento predeterminado de un evento de clic de marcador consiste en mostrar su ventana
    de información (si está disponible) y mover la cámara de modo que el marcador quede centrado en el mapa.

    */



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

    public void onGetIncidenciasStart(){
        pDEspera = ProgressDialog.show(this, "Espere","Obteniendo Incidencias...",true);
    }

    public void onGetIncidenciasSuccess(){
        pDEspera.dismiss();

    }


}

