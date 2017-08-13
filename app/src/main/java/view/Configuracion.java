package view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.francisco.drivealert.R;

import data.Param;

public class Configuracion extends AppCompatActivity {

    public TextView tvConfig;
    public TextView tvIdioma;
    public ImageButton btEsp;
    public ImageButton btIng;
    public ImageButton btIta;
    public CheckBox cbIncidencias;

    Param p;

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

        cbIncidencias = (CheckBox) findViewById(R.id.cBIncidencias);

        if (cbIncidencias.isChecked()){ p.setActivarincidencias(true); } else { p.setActivarincidencias(false);}

    }
}
