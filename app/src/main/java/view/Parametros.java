package view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.project.francisco.drivealert.R;

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

        btIncidencias = (Button) findViewById(R.id.btIncidencias);

        // Abrir Activity para configurar las Incidencias
        btIncidencias.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Parametros.this, Incidencias.class);
                startActivity(i);
            }
        });

        // boolean estado = Rb.isCheked();
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
