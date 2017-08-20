package view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.francisco.drivealert.R;

public class Configuracion extends AppCompatActivity {

    public TextView tvConfig;
    public TextView tvIdioma;
    public ImageButton btEsp;
    public ImageButton btIng;
    public ImageButton btIta;
    public CheckBox cbIncidencias;
    public Button btConfParam;
    public Button btConfIncidencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        tvConfig = (TextView) findViewById(R.id.tvConfig);
        tvIdioma = (TextView) findViewById(R.id.tvIdioma);

        // Por ahora la aplicación se cambia sola de Idioma, viendo el idioma del teléfono,
        // pero está la opción de que se pueda cambiar por si solo (digo yo)
        btEsp = (ImageButton) findViewById(R.id.ibEsp);
        btIng = (ImageButton) findViewById(R.id.ibIng);
        btIta = (ImageButton) findViewById(R.id.ibIta);

        //cbIncidencias = (CheckBox) findViewById(R.id.cBIncidencias);
        //cbIncidencias.setChecked(false);

        btConfParam = (Button) findViewById(R.id.btConfParam);
        btConfParam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Configuracion.this, Parametros.class);
                startActivity(i);
            }
        });
        btConfIncidencias = (Button) findViewById(R.id.btConfIncidencias);
        //btConfIncidencias.setVisibility(View.GONE);
        btConfIncidencias.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Configuracion.this, Incidencias.class);
                startActivity(i);
            }
        });

        //Param p = new Param();
    }
    /*
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.cBIncidencias:
                if (checked)
                    btConfIncidencias.setVisibility(View.VISIBLE);
            else
                    btConfIncidencias.setVisibility(View.GONE);
                break;
        }
    }
    */
}
