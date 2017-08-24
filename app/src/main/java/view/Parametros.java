package view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.project.francisco.drivealert.R;

import data.model.Incidencia;
import data.model.Param;

/*
    CLASE PARAM - Para mostrar y donde se eligen los parámetros de la aplicación para la
    busqueda de la ruta.
 */
public class Parametros extends AppCompatActivity {

    public TextView tvParam;
    public TextView tvRuta;
    public RadioGroup GrbGrupoRuta;
    public RadioButton RbOpcionCar;
    public RadioButton RbOpcionPie;
    public RadioButton RbOpcionBici;
    public RadioButton RbOpcionPublic;

    public TextView tvPublic;
    public RadioGroup GrbGrupoPublic;
    public RadioButton RbOpcionBus;
    public RadioButton RbOpcionTren;

    public CheckBox cBPeaje;
    public CheckBox cBAutovia;
    public CheckBox cBFerry;

    public Button btGuardar;
    public Button btIncidencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param);

        tvParam = (TextView) findViewById(R.id.tvParam);
        tvRuta = (TextView) findViewById(R.id.tvRuta);
        GrbGrupoRuta = (RadioGroup) findViewById(R.id.GrbGrupoRuta);
        GrbGrupoRuta.clearCheck();
        GrbGrupoRuta.check(R.id.RbOpcionCar);
        RbOpcionCar = (RadioButton) findViewById(R.id.RbOpcionCar);
        RbOpcionPie = (RadioButton) findViewById(R.id.RbOpcionPie);
        RbOpcionBici = (RadioButton) findViewById(R.id.RbOpcionBici);
        RbOpcionPublic = (RadioButton) findViewById(R.id.RbOpcionPublic);

        tvPublic = (TextView) findViewById(R.id.tvPublic);
        tvPublic.setVisibility(View.GONE);
        GrbGrupoPublic = (RadioGroup) findViewById(R.id.GrbGrupoPublic);
        GrbGrupoPublic.setVisibility(View.GONE);

        RbOpcionBus = (RadioButton) findViewById(R.id.RbOpcionBus);
        RbOpcionTren = (RadioButton) findViewById(R.id.RbOpcionTren);

        cBPeaje = (CheckBox) findViewById(R.id.cBPeaje);
        cBAutovia = (CheckBox) findViewById(R.id.cBAutovia);
        cBFerry = (CheckBox) findViewById(R.id.cBFerry);
        btGuardar = (Button) findViewById(R.id.btGuardar);
        btIncidencias = (Button) findViewById(R.id.btIncidencias);
        CargarPreferencias();

        btIncidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Parametros.this, Incidencias.class);
                startActivity(i);
            }
        });

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuardarPreferencias();
                Toast saved = Toast.makeText(getApplicationContext(),
                        getApplicationContext().getString(R.string.savedConf), Toast.LENGTH_SHORT);
                saved.show();

            }
        });
    }

    public void CargarPreferencias() {
        SharedPreferences UserConfiguration = getSharedPreferences("UserConfiguration", Context.MODE_PRIVATE);
        RbOpcionCar.setChecked(UserConfiguration.getBoolean("OpcionCar",true));
        RbOpcionBici.setChecked(UserConfiguration.getBoolean("OpcionBici",false));
        RbOpcionPie.setChecked(UserConfiguration.getBoolean("OpcionPie",false));
        RbOpcionPublic.setChecked(UserConfiguration.getBoolean("OpcionPublic",false));

        RbOpcionBus.setChecked(UserConfiguration.getBoolean("OpcionPublicBus",true));
        RbOpcionTren.setChecked(UserConfiguration.getBoolean("OpcionPublicTren",false));

        cBPeaje.setChecked(UserConfiguration.getBoolean("Peaje",true));
        cBAutovia.setChecked(UserConfiguration.getBoolean("Autovia",false));
        cBFerry.setChecked(UserConfiguration.getBoolean("Ferry",true));
    }

    public void GuardarPreferencias (){
        SharedPreferences UserConfiguration = getSharedPreferences("UserConfiguration", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = UserConfiguration.edit();
        editor.putBoolean("OpcionCar",RbOpcionCar.isChecked());
        editor.putBoolean("OpcionBici",RbOpcionBici.isChecked());
        editor.putBoolean("OpcionPie",RbOpcionPie.isChecked());
        editor.putBoolean("OpcionPublic",RbOpcionPublic.isChecked());

        editor.putBoolean("OpcionPublicBus",RbOpcionBus.isChecked());
        editor.putBoolean("OpcionPublicTren",RbOpcionTren.isChecked());

        editor.putBoolean("Peaje",cBPeaje.isChecked());
        editor.putBoolean("Autovia",cBAutovia.isChecked());
        editor.putBoolean("Ferry",cBFerry.isChecked());

        editor.commit();
    }

    public void onRadioButtonClicked(View view) {
        boolean marcado = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.RbOpcionPublic:
                if (marcado) {
                    mostrarPublico();
                }
                break;
            case R.id.RbOpcionBici:
                if (marcado) {
                    noMostrarPublico();
                }
                break;
            case R.id.RbOpcionCar:
                if (marcado) {
                    noMostrarPublico();
                }
                break;
            case R.id.RbOpcionPie:
                if (marcado) {
                    noMostrarPublico();
                }
                break;
        }
    }

    private void mostrarPublico() {
        tvPublic.setVisibility(View.VISIBLE);
        GrbGrupoPublic.setVisibility(View.VISIBLE);
    }
    private void noMostrarPublico() {
        tvPublic.setVisibility(View.GONE);
        GrbGrupoPublic.setVisibility(View.GONE);
    }


}
