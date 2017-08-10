package view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
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
import java.util.ArrayList;
import java.util.List;

import logic.DirectionFinder;
import logic.DirectionFinderListener;
import data.Ruta;
import logic.GetIncidencias;

public class MostrarRuta extends AppCompatActivity implements OnMapReadyCallback, DirectionFinderListener {

    public TextView tvOrigShow;
    public TextView tvDestShow;
    public TextView tvDistance;
    public TextView tvDuration;
    public ImageView ivDistance;
    public ImageView ivDuration;
    public ImageView ivGo;
    public SupportMapFragment map;
    public GoogleMap mMap;
    public Button btObtenerIncidencias;

    private List<Marker> origenMarkers = new ArrayList<>();
    private List<Marker> destinoMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog pDEspera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_route);
        map = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        map.getMapAsync(this);

        tvOrigShow = (TextView) findViewById(R.id.tvOrigShow);
        tvDestShow = (TextView) findViewById(R.id.tvDestShow);
        tvDistance = (TextView) findViewById(R.id.tvDistance);
        tvDuration = (TextView) findViewById(R.id.tvDuration);
        ivDistance = (ImageView) findViewById(R.id.ivDistance);
        ivDuration = (ImageView) findViewById(R.id.ivDuration);
        ivGo = (ImageView) findViewById(R.id.ivGo);
        btObtenerIncidencias = (Button) findViewById(R.id.btObtenerIncidencias);

        // Recibe el Origen y Destino
        Bundle extras = getIntent().getExtras();
        String OrigenRuta = extras.getString("origen");
        String DestinoRuta = extras.getString("destino");
        tvOrigShow.setText(OrigenRuta);
        tvDestShow.setText(DestinoRuta);

        try {
            new DirectionFinder(this, OrigenRuta, DestinoRuta).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
    }

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
    public void onDirectionFinderSuccess(List<Ruta> rutas) {
        pDEspera.dismiss();
        polylinePaths = new ArrayList<>();
        origenMarkers = new ArrayList<>();
        destinoMarkers = new ArrayList<>();

        for (Ruta ruta : rutas) {
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

    // CREAR SI ES PRECIO UN LISTENER PARA LAS INCIDENCIAS

}


