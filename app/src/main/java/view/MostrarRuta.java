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

import di.library.BaseActivity;
import logic.DirectionFinder;
import logic.DirectionFinderListener;
import data.model.Ruta;
import logic.GetIncidencias;


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
    private Ruta selectedRoute = injector.getRouteDataSource().get();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_route);

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

        btObtenerIncidencias.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Ejecutar el algoritmo de las incidencias
                try {
                    new GetIncidencias().execute();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        setUpMapIfNeeded();
        loadDirectionFromRoute();
    }

    private void setUpMapIfNeeded() {
        if (mMap != null) return;
        map.getMapAsync(this);
    }

    protected void loadDirectionFromRoute() {
        tvOrigShow.setText(selectedRoute.origen);
        tvDestShow.setText(selectedRoute.destino);

        try {
            String directionURL = getURLDirectionFrom(selectedRoute);
            new DirectionFinder(this, directionURL).execute();
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

        return  getString(R.string.direction_url_api)
                + "origin=" + originEncode
                + "&destination=" + destinationEncode
                + "&key=" + getString(R.string.google_maps_api_key);
    }

    protected void updateGoogleMap() {
        if (mMap == null || this.rutas == null) return;

        polylinePaths  = new ArrayList<>();
        origenMarkers  = new ArrayList<>();
        destinoMarkers = new ArrayList<>();

        for (Ruta ruta : this.rutas) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ruta.latorigen, 16));
            ((TextView) findViewById(R.id.tvDuration)).setText(ruta.duracionStr); //ruta.dur.text
            ((TextView) findViewById(R.id.tvDistance)).setText(ruta.distanciaStr); // ruta.dist.text

            origenMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(ruta.origen)
                    .position(ruta.latorigen)));
            destinoMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(ruta.destino)
                    .position(ruta.latdestino)));
            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < ruta.PuntosRuta.size(); i++)
                polylineOptions.add(ruta.PuntosRuta.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

    private void updateDatasource(Ruta route) {
        injector.getRouteDataSource().save(route);
    }

    // MARCADOR CLICKABLE PARA MOSTRAR LA DESCRIPCION DE LA INCIDENCIA
    /*
    public class MarkerDemoActivity extends android.support.v4.app.FragmentActivity implements OnMarkerClickListener
    {
        private Marker myMarker;

        private void setUpMap()
        {
            .......
            googleMap.setOnMarkerClickListener(this);

            myMarker = googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("My Spot")
                        .snippet("This is my spot!")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            ......
        }

        @Override
        public boolean onMarkerClick(final Marker marker) {

            if (marker.equals(myMarker))
            {
                //handle click here
            }
        }
    }
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng grn = new LatLng(37.1793, -3.5995);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(grn, 18));
        origenMarkers.add(mMap.addMarker(new MarkerOptions().title("Granada").position(grn)));

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
        updateDatasource(rutas.size() > 0 ? rutas.get(0) : null);
    }

    // CREAR UN LISTENER PARA LAS INCIDENCIAS,
    // Comparar cada PUNTO DE LA RUTA guardado con alguna incidencia por si coincide y mostrar esa incidencia.

}

