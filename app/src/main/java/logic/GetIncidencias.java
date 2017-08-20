package logic;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

/*
Esta clase es la encargada de recopilar las incidencias de la web de tráfico
 */
public class GetIncidencias {

    // Ejecución de la búsqueda de las incidencias
    public void execute() throws UnsupportedEncodingException {
        // new JsonTask().execute((Runnable) new GetURLTrafico());
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
                InputStream stream = new ByteArrayInputStream(res.getBytes("UTF-8"));
                new ParserJSON().leerFlujoJsonIncidencia(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        }
    }
