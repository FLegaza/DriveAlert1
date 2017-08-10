package view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;
import android.widget.TextView;

import com.project.francisco.drivealert.R;

public class Incidencias extends AppCompatActivity {

    public TextView tvIncidencias;
    public Switch SwCamaras;
    public Switch SwSensores;
    public Switch SwRadar;
    public Switch SwReten;
    public Switch SwObra;

    public String cam;
    public String sen;
    public String radar;
    public String reten;
    public String obra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidence);

        tvIncidencias = (TextView) findViewById(R.id.tvIncidencias);
        SwCamaras = (Switch) findViewById(R.id.SwCamaras);
        SwSensores = (Switch) findViewById(R.id.SwSensores);
        SwRadar = (Switch) findViewById(R.id.SwRadar);
        SwReten = (Switch) findViewById(R.id.SwReten);
        SwObra = (Switch) findViewById(R.id.SwObra);

    }

    // NO CONSIGO GUARDAR LA CONFIGURACIÓN PARA QUE AL CAMBIAR DE ACTIVITY NO SE CAMBIE LA CONFIGURACIÓN
    // LO SUYO SERÍA GUARDAR LA CONFIGURACIÓN EN SQLITE PARA GUARDAR LA CONFIGURACIÓN PERMANENTEMENTE

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putAll(savedInstanceState);
        /*
        savedInstanceState.putString(SwCamaras.toString(), cam);
        savedInstanceState.putString(SwSensores.toString(), sen);
        savedInstanceState.putString(SwRadar.toString(), radar);
        savedInstanceState.putString(SwReten.toString(), reten);
        savedInstanceState.putString(SwObra.toString(), obra);
        super.onSaveInstanceState(savedInstanceState);
          */

    }
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        /*
        cam = savedInstanceState.getString(SwCamaras.toString());
        sen = savedInstanceState.getString(SwSensores.toString());
        radar = savedInstanceState.getString(SwRadar.toString());
        reten = savedInstanceState.getString(SwReten.toString());
        obra = savedInstanceState.getString(SwObra.toString());
        */
    }


}
