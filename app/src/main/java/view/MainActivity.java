package view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.project.francisco.drivealert.R;

import io.realm.Realm;

/*  CLASE_PRINCIPAL MainActivity
    Clase-Activity con la cual arranca la aplicación.
 */
public class MainActivity extends AppCompatActivity {

    public Button btnNuevaRuta;
    public Button btnRutasGuardadas;
    public Button btnConfiguracion;
    public Button btnSalir;
    public ImageButton ibtnAyuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);

        btnNuevaRuta = (Button) findViewById(R.id.btnNuevaRuta);
        btnRutasGuardadas = (Button) findViewById(R.id.btnRutasGuardadas);
        btnConfiguracion = (Button) findViewById(R.id.btnConfiguracion);
        btnSalir = (Button) findViewById(R.id.btnSalir);
        ibtnAyuda = (ImageButton) findViewById(R.id.ibtnAyuda);

        // Abrir Activity de Nueva Ruta
        btnNuevaRuta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, SelectRuta.class);
                startActivity(i);
            }
        });

        // Abrir Activity de Rutas Guardadas
        btnRutasGuardadas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, RutasGuardadas.class);
                startActivity(i);
            }
        });

        // Abrir Activity de Configuración
        btnConfiguracion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, Configuracion.class);
                startActivity(i);
            }
        });

        // Abrir Activity de Salir de la aplicación
        btnSalir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        // Abrir Activity de Ayuda
        ibtnAyuda.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
                Intent i = new Intent(MainActivity.this, ViewAyuda.class);
                startActivity(i);
            }
        });

    }

}
