package logic;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import data.Incidencia;
import data.Param;
import logic.ParserJSON;

/*
Esta clase es la encargada de recopilar las incidencias de la web de tráfico
 */
public class GetIncidencias {

    // Coger parámetros


    // Ejecución de la búsqueda de las incidencias
    public void execute() throws UnsupportedEncodingException {
        // Recoger Parámetros y pasarselos a la función GetURLTrafico)
        //Param par = new Param();
        //new DownloadRawData().execute(new GetURLTrafico(par));
        // Pasarle la URL para coger los datos.
    }

    private class DownloadRawData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String link = params[0];

            try {
                URL url = new URL(link);
                InputStream is = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                return buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            try {
                InputStream stream = new ByteArrayInputStream(res.getBytes("UTF-8")); //Qué hace?
                new ParserJSON().leerFlujoJsonIncidencia(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
