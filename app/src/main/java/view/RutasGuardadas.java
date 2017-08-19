package view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.project.francisco.drivealert.R;


/*
CLASE SAVEROUTES - Muestra las rutas guardadas en la BD
 */
public class RutasGuardadas extends AppCompatActivity {

    public ListView lvRutas;
    public TextView tvRutas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_routes);

        lvRutas = (ListView) findViewById(R.id.lvRutas);
        tvRutas = (TextView) findViewById(R.id.tvRutas);

        // LIST VIEW LA MEJOR OPCIÓN para Mostrar una lista de:
        // RUTA 1 - Origen-Destino - Km - Tiempo - Incidencias: 2)
    }



}
