package view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    /*
    public TextView tvOrigen;
    public TextView tvDestino;
    public TextView tvDuracion;
    public TextView tvDistancia;
    public TextView tvIncidencias;
    */

    private RouteRepository repository = injector.getRouteRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_routes);

        lvRutas = (ListView) findViewById(R.id.lvRutas);
        tvRutas = (TextView) findViewById(R.id.tvRutas);
        /*
        tvOrigen = (TextView) findViewById(R.id.tvOrigen);
        tvDestino = (TextView) findViewById(R.id.tvDestino);
        tvDuracion = (TextView) findViewById(R.id.tvDuracion);
        tvDistancia = (TextView) findViewById(R.id.tvDistancia);
        tvIncidencias = (TextView) findViewById(R.id.tvIncidencias);

        tvOrigen.setText("Granada");
        tvDestino.setText("Madrid");
        tvDuracion.setText("4h 30min");
        tvDistancia.setText("338km");
        tvIncidencias.setText("0");
        final String[] datos =
                new String[]{tvOrigen.getText().toString(),
                        tvDestino.getText().toString(),
                        tvDuracion.getText().toString(),
                        tvDistancia.getText().toString(),
                        tvIncidencias.getText().toString()};

        */

        final String[] datos2 = new String[]{"Elem1","Elem2","Elem3","Elem4","Elem5"};

        //Adaptador
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, datos2);
        // ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, R.layout.activity_saved_routes_route, datos);

        // AdaptadorRutas adaptador = new AdaptadorRutas(this, datos);

        lvRutas.setAdapter(adaptador);

        lvRutas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"Ruta "+i,Toast.LENGTH_SHORT).show();
            }
        });


        // reloadRoutesTableView(); // Se me cierra la aplicación, supongo que será porque no encuentra rutas guardadas
    }
    /*
    class AdaptadorRutas extends ArrayAdapter<String> {

        public AdaptadorRutas(Context context, String[] datos) {
            super(context, R.layout.activity_saved_routes_route, datos);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.activity_saved_routes_route, null);

            tvOrigen = (TextView) findViewById(R.id.tvOrigen);
            tvOrigen.setText("Granada");
            tvDestino = (TextView) findViewById(R.id.tvDestino);
            tvDestino.setText("Madrid");
            tvDuracion = (TextView) findViewById(R.id.tvDuracion);
            tvDuracion.setText("4h 30min");
            tvDistancia = (TextView) findViewById(R.id.tvDistancia);
            tvDistancia.setText("338km");
            tvIncidencias = (TextView) findViewById(R.id.tvIncidencias);
            tvIncidencias.setText("0");

            return(item);
        }

    }
    */
    /*
    private void reloadRoutesTableView() {

        for (Ruta ruta:repository.getListRoutes()) {
            String origen  = ruta.origen;
            String destino = ruta.destino;
            String km = ruta.distanciaStr;
            String tiempo = ruta.duracionInt.toString();
            int a = 0;
        }
    }
    */

}
