package view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.project.francisco.drivealert.R;

import data.datasource.RouteDataSource;
import data.model.Ruta;
import data.repository.RouteRepository;
import di.Injector;
import di.library.BaseActivity;

/*  CLASE SelectRuta
    Clase-Activity donde se selecciona el origen y destino de la ruta para la que se quieren conocer
    distancia, duración y las posibles incidencias de ésta.
 */
public class SelectRuta extends BaseActivity {

    public TextView tvOrig;
    public TextView tvDest;
    public EditText etOrig;
    public EditText etDest;
    public Button btnIr;
    public Button btnParam;

    private RouteRepository repository = injector.getRouteRepository();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orig_dest);

        tvOrig = (TextView) findViewById(R.id.tvOrig);
        tvDest = (TextView) findViewById(R.id.tvDest);
        etOrig = (EditText) findViewById(R.id.etIntrOrig);
        etDest = (EditText) findViewById(R.id.etIntrDest);
        btnIr = (Button) findViewById(R.id.btnIr);
        btnParam = (Button) findViewById(R.id.btnParam);

        // Abrir Activity para seleccionar parámetros
        btnParam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(SelectRuta.this, Parametros.class);
                startActivity(i);
            }
        });

        // Abrir Activity para mostrar la Ruta
        btnIr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Ruta ruta = getRouteFromFields();
                if (ruta == null) {
                    // TODO alert with invalid data!
                } else {
                    repository.setSelected(ruta);
                    Intent i = new Intent(SelectRuta.this, MostrarRuta.class);
                    startActivity(i);
                }
            }
        });

    }

    private Ruta getRouteFromFields() {
        String orig = etOrig.getText().toString();
        String dest = etDest.getText().toString();
        if (orig.isEmpty() || dest.isEmpty()) return null;

        Ruta ruta = new Ruta();
        ruta.setOrigen(orig);
        ruta.setDestino(dest);

        return ruta;
    }
}
