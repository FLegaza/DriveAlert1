package view;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.drive.Drive;
import com.project.francisco.drivealert.R;

import sqlite.BaseDeDatos;

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

        btnNuevaRuta = (Button) findViewById(R.id.btnNuevaRuta);
        btnRutasGuardadas = (Button) findViewById(R.id.btnRutasGuardadas);
        btnConfiguracion = (Button) findViewById(R.id.btnConfiguracion);
        btnSalir = (Button) findViewById(R.id.btnSalir);
        ibtnAyuda = (ImageButton) findViewById(R.id.ibtnAyuda);

        // PRUEBA PARA LA BASE DE DATOS
        //Abrimos la base de datos en modo escritura
        // BaseDeDatos usdbh = new BaseDeDatos(this);
        // SQLiteDatabase db = usdbh.getReadableDatabase();

        /*
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {

            Insertamos 5 usuarios de ejemplo

            for(int i=1; i<=5; i++)
            {
                //Generamos los datos
                int codigo = i;
                String nombre = "Usuario" + i;
                //Insertamos los datos en la tabla Usuarios
                db.execSQL("INSERT INTO Usuarios (codigo, nombre) " +
                        "VALUES (" + codigo + ", '" + nombre +"')");
            }

            //Cerramos la base de datos
            db.close();
        }
        */

        // Prueba para mostrar Toast en la Ayuda
        ibtnAyuda.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
                Toast.makeText(getBaseContext(), "No hay ayuda!" , Toast.LENGTH_SHORT).show();
            }
        });

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
                //Intent intent = new Intent(Intent.ACTION_MAIN);
                //intent.addCategory(Intent.CATEGORY_HOME);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(intent);
            }
        });

    }
}
