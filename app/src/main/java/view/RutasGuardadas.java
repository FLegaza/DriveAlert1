package view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.project.francisco.drivealert.R;

import java.util.List;
import java.util.StringTokenizer;

import data.model.Incidencia;
import data.model.Ruta;
import data.repository.RouteRepository;
import di.library.BaseActivity;
import io.realm.RealmList;


/*
CLASE SAVEROUTES - Muestra las rutas guardadas en la BD
 */
public class RutasGuardadas extends BaseActivity {

    public ListView lvRutas;
    public TextView tvRutas;

    private RouteRepository repository = injector.getRouteRepository();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_routes);

        lvRutas = (ListView) findViewById(R.id.lvRutas);
        tvRutas = (TextView) findViewById(R.id.tvRutas);

        reloadRoutesTableView();
    }

    private void reloadRoutesTableView() {
        // LIST VIEW LA MEJOR OPCIÓN para Mostrar una lista de:

        for (Ruta ruta:repository.getListRoutes()) {
            // RUTA X - Origen-Destino - Km - Tiempo - Incidencias: 2)
            String origen  = ruta.origen;
            String destino = ruta.destino;
            String km = ruta.distanciaStr;
            String tiempo = ruta.duracionInt.toString();
            int a = 0;
        }
    }
}
