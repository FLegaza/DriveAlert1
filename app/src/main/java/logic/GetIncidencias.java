package logic;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import data.Incidencia;

/*
Esta clase es la encargada de recopilar las incidencias de la web de tráfico
 */
public class GetIncidencias {

    HttpURLConnection con;

    // Ejecución de la búsqueda de las incidencias
    public void execute() throws UnsupportedEncodingException {
        new JsonTask().execute((Runnable) new GetURLTrafico());
        // Pasarle la URL para coger los datos.
    }

    public class JsonTask extends AsyncTask<URL, Void, List<Incidencia>> {

        @Override
        protected List<Incidencia> doInBackground(URL... urls) {
            List<Incidencia> incidencias = null;

            try {
                // Establecer la conexión
                con = (HttpURLConnection)urls[0].openConnection();
                con.setConnectTimeout(15000);
                con.setReadTimeout(10000);
                // Obtener el estado del recurso
                int statusCode = con.getResponseCode();
                if(statusCode!=200) {
                    incidencias = new ArrayList<>();
                    incidencias.add(new Incidencia(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null));
                } else {
                    // Parsear el flujo con formato JSON
                    InputStream in = new BufferedInputStream(con.getInputStream());
                    // JsonAnimalParser parser = new JsonAnimalParser();
                    ParserJSON parser = new ParserJSON();
                    incidencias = parser.leerFlujoJsonIncidencia(in);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                con.disconnect();
            }
            return incidencias;
        }

        @Override
        protected void onPostExecute(List<Incidencia> incidencias) {
            // Asignar los objetos de Json parseados al adaptador
            if(incidencias!=null) {

            }else{
                //Toast.makeText(getBaseContext(), "Ocurrió un error de Parsing Json", Toast.LENGTH_SHORT).show();
                // No se porque da ese error, cuando en MainActivity no lo da
            }

        }
    }
}
