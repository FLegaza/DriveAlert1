package logic;

import android.os.AsyncTask;
import android.util.JsonReader;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import data.model.Incidencia;
import data.model.Param;

/*
Esta clase es la encargada de recopilar las incidencias de la web de tráfico
 */
public class GetIncidencias {

    private GetIncidenciasListener listener;
    private String url;

    public GetIncidencias(GetIncidenciasListener listener, String url) {
        this.listener = listener;
        this.url = url;
    }

    // Ejecución de la búsqueda de las incidencias
    public void execute() throws UnsupportedEncodingException {
        listener.onGetIncidenciasStart();
        new DownloadRawData().execute(this.url);
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
                List<Incidencia> incidencias;
                incidencias = leerFlujoJsonIncidencia(stream);
                listener.onGetIncidenciasSuccess(incidencias);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // ------- JSON -------

    // Parser JSON para las INCIDENCIAS
    private List<Incidencia> leerFlujoJsonIncidencia(InputStream in) throws IOException {
        // Nueva instancia JsonReader
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            // Leer Array
            return leerArrayIncidencias(reader);
        } finally {
            reader.close();
        }
    }

    private List<Incidencia> leerArrayIncidencias(JsonReader reader) throws IOException {
        // Lista temporal
        ArrayList<Incidencia> incidencias = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            // Leer objeto
            incidencias.add(leerIncidencia(reader));
        }
        reader.endArray();
        return incidencias;
    }

    private Incidencia leerIncidencia(JsonReader reader) throws IOException {
        // Variables locales
        String carretera = null;
        Integer estado = null;
        String alias = null;
        String sentido = null;
        Double PK = null;
        String tipo = null;
        Double lng = null;
        String codEle = null;
        Double lat = null;
        String precision = null;
        String fecha = null;
        String poblacion = null;
        String descripcion = null;
        String fechaFin = null;
        String tipoInci = null;
        Integer pkFinal = null;
        String provincia = null;
        String causa = null;
        String hora = null;
        Integer pkIni = null;
        String icono = null;
        String horaFin = null;
        String nivel = null;

        // Iniciar objeto
        reader.beginObject();

        // Lectura de cada atributo
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "carretera":
                    carretera = reader.nextString();
                    break;
                case "estado":
                    estado = reader.nextInt();
                    break;
                case "alias":
                    alias = reader.nextString();
                    break;
                case "sentido":
                    sentido = reader.nextString();
                    break;
                case "PK":
                    PK = reader.nextDouble();
                    break;
                case "tipo":
                    tipo = reader.nextString();
                    break;
                case "lng":
                    lng = reader.nextDouble();
                    break;
                case "codEle":
                    codEle = reader.nextString();
                    break;
                case "lat":
                    lat = reader.nextDouble();
                    break;
                case "precision":
                    precision = reader.nextString();
                    break;
                case "fecha":
                    fecha = reader.nextString();
                    break;
                case "poblacion":
                    poblacion = reader.nextString();
                    break;
                case "descripcion":
                    descripcion = reader.nextString();
                    break;
                case "fechaFin":
                    fechaFin = reader.nextString();
                    break;
                case "tipoInci":
                    tipoInci = reader.nextString();
                    break;
                case "pkFinal":
                    pkFinal = reader.nextInt();
                    break;
                case "provincia":
                    provincia = reader.nextString();
                    break;
                case "causa":
                    causa = reader.nextString();
                    break;
                case "hora":
                    hora = reader.nextString();
                    break;
                case "pkIni":
                    pkIni = reader.nextInt();
                    break;
                case "icono":
                    icono = reader.nextString();
                    break;
                case "horaFin":
                    horaFin = reader.nextString();
                    break;
                case "nivel":
                    nivel = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Incidencia(carretera, estado, alias, sentido, PK, tipo, lng, codEle, lat,
                precision, fecha, poblacion, descripcion, fechaFin, tipoInci, pkFinal, provincia,
                causa, hora, pkIni, icono, horaFin, nivel);
    }
}
