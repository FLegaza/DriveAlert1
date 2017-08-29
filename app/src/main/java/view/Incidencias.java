package view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.project.francisco.drivealert.R;

import data.model.Param;

public class Incidencias extends AppCompatActivity {

    public TextView tvIncidencias;
    public Switch SwCamaras;
    public Switch SwSensores;
    public Switch SwRadar;
    public Switch SwReten;
    public Switch SwObra;
    public Button btGuardarInci;

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
        btGuardarInci = (Button) findViewById(R.id.btGuardarInc);
        CargarPreferencias();

        btGuardarInci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuardarPreferencias();
                Toast saved = Toast.makeText(getApplicationContext(),
                        getApplicationContext().getString(R.string.savedConf), Toast.LENGTH_SHORT);
                saved.show();
            }
        });

    }

    private void CargarPreferencias() {
        SharedPreferences UserConfiguration = getSharedPreferences("UserConfiguration", Context.MODE_PRIVATE);
        SwCamaras.setChecked(UserConfiguration.getBoolean("Camaras",true));
        SwSensores.setChecked(UserConfiguration.getBoolean("Sensores",true));
        SwRadar.setChecked(UserConfiguration.getBoolean("Radares",true));
        SwReten.setChecked(UserConfiguration.getBoolean("Retenciones",true));
        SwObra.setChecked(UserConfiguration.getBoolean("Obras",true));
    }

    private void GuardarPreferencias (){
        SharedPreferences UserConfiguration = getSharedPreferences("UserConfiguration", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = UserConfiguration.edit();
        editor.putBoolean("Camaras",SwCamaras.isChecked());
        editor.putBoolean("Sensores",SwSensores.isChecked());
        editor.putBoolean("Radares",SwRadar.isChecked());
        editor.putBoolean("Retenciones",SwReten.isChecked());
        editor.putBoolean("Obras",SwObra.isChecked());
        editor.apply(); // edit.commit();
    }

}
