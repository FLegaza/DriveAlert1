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
import data.model.Ruta;

/*
Esta clase es la encargada de recopilar las incidencias de la web de tráfico
 */
public class UpdateRoutesWithTrafficEvents {

    private String url;
    private List<Ruta> routes;
    private UpdateTrafficEvents callback;

    public UpdateRoutesWithTrafficEvents(UpdateTrafficEvents callback, List<Ruta> routes, String url) {
        this.routes = routes;
        this.url = url;
        this.callback = callback;
    }

    // Ejecución de la búsqueda de las incidencias
    public void execute() throws UnsupportedEncodingException {
        if (routes == null || routes.size() <= 0) {
            callback.onUpdateTrafficEventsFailure();
        } else {
            callback.onUpdateTrafficEventsStart();
            new DownloadRawData().execute(this.url);
        }
    }

    /*
    private List<Incidencia> compareIncidencias (List<LatLng> PuntosRuta, List<Incidencia> incidencias) {

        List<Incidencia> incidenciasRuta;
        // Comparar con las funciones:

        static void distanceBetween(double startLatitude, double startLongitude, double endLatitude, double endLongitude, float[] results)

        // float 	distanceTo(Location dest)
        // (Se devuelve en metros) - Debería ser unos 30Km de cercanía para que se viese

        return incidenciasRuta;
    }
    */

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
                List<Incidencia> trafficEvents = leerFlujoJsonIncidencia(stream);
                updateRoutes(trafficEvents);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void updateRoutes(List<Incidencia> trafficEvents) {
        ArrayList<Incidencia> filteredTrafficEvents = new ArrayList<>();

        for (Ruta route: this.routes) {
            List<Incidencia> filtered = route.filterTrafficEvents(trafficEvents);
            if (filtered != null) {
                filteredTrafficEvents.addAll(filtered);
                route.setIncidenciasRuta(filtered);
            }
        }

        callback.onUpdateTrafficEventsSuccess(filteredTrafficEvents);
    }

    // Parser JSON para las INCIDENCIAS
    private List<Incidencia> leerFlujoJsonIncidencia(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return leerArrayIncidencias(reader);
        } finally {
            reader.close();
        }
    }

    private List<Incidencia> leerArrayIncidencias(JsonReader reader) throws IOException {
        ArrayList<Incidencia> trafficEvents = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            Incidencia trafficEvent = leerIncidencia(reader);
            trafficEvents.add(trafficEvent);
        }
        reader.endArray();

        return trafficEvents;
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
                    String valuePKFin = reader.nextString().replace(" ", "");
                    if (!valuePKFin.isEmpty()) {
                        pkFinal = Integer.parseInt(valuePKFin);
                    }
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
                    String valuePKIni = reader.nextString().replace(" ", "");
                    if (!valuePKIni.isEmpty()) {
                        pkIni = Integer.parseInt(valuePKIni);
                    }
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
