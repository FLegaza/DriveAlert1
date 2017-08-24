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

        btConfParam = (Button) findViewById(R.id.btConfParam);
        btConfIncidencias = (Button) findViewById(R.id.btConfIncidencias);

        btConfParam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Configuracion.this, Parametros.class);
                startActivity(i);
            }
        });

        btConfIncidencias.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Configuracion.this, Incidencias.class);
                startActivity(i);
            }
        });

    }
}
