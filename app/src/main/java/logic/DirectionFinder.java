package logic;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import data.Ruta;
import logic.DirectionFinderListener;

import static com.project.francisco.drivealert.R.string.google_maps_api_key;
import static com.project.francisco.drivealert.R.string.direction_url_api;

/*
    CLASE DIRECTION FINDER - Clase que se encarga de (crear la URL) para coger el JSON, parsearlo y
    almacenarlo en la clase RUTA
 */
public class DirectionFinder {

    private DirectionFinderListener listener;
    private String origen;
    private String destino;

    // Constructor
    public DirectionFinder(DirectionFinderListener listener, String origen, String destino) {
        this.listener = listener;
        this.origen = origen;
        this.destino = destino;
    }

    // Ejecución de la búsqueda de la Ruta
    public void execute() throws UnsupportedEncodingException {
        listener.onDirectionFinderStart();
        new DownloadRawData().execute(createUrl());
    }

    // Creación de la URL para la web desde donde obtener los datos
    private String createUrl() throws UnsupportedEncodingException {
        String urlOrigen = URLEncoder.encode(origen, "utf-8");
        String urlDestino = URLEncoder.encode(destino, "utf-8");
        return direction_url_api + "origin=" + urlOrigen +
                "&destination=" + urlDestino + "&key=" + google_maps_api_key;
    }

    // Función para recoger toda la información de la página web
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
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            try {
                parseJSon(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // Analisis de los Datos JSON
    private void parseJSon(String data) throws JSONException {
        if (data == null)
            return;

        List<Ruta> rutas = new ArrayList<Ruta>();
        JSONObject jsonData = new JSONObject(data);
        JSONArray jsonRutas = jsonData.getJSONArray("routes");
        for (int i = 0; i < jsonRutas.length(); i++) {
            JSONObject jsonRuta = jsonRutas.getJSONObject(i);
            Ruta ruta = new Ruta();

            JSONObject overview_polylineJson = jsonRuta.getJSONObject("overview_polyline");
            JSONArray jsonLegs = jsonRuta.getJSONArray("legs");
            JSONObject jsonLeg = jsonLegs.getJSONObject(0);
            JSONObject jsonDistancia = jsonLeg.getJSONObject("distance");
            JSONObject jsonDuracion = jsonLeg.getJSONObject("duration");
            JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");
            JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");

            // Cambiar por funciones SET - GET
            ruta.distanciaStr = jsonDistancia.getString("text");
            ruta.distanciaInt =  jsonDistancia.getInt("value");
            ruta.duracionStr = jsonDuracion.getString("text");
            ruta.duracionInt = jsonDuracion.getInt("value");
            ruta.destino = jsonLeg.getString("end_address");
            ruta.origen = jsonLeg.getString("start_address");
            ruta.latorigen = new LatLng(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng"));
            ruta.latdestino = new LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng"));
            ruta.PuntosRuta = decodePolyLine(overview_polylineJson.getString("points"));

            rutas.add(ruta);
        }
        listener.onDirectionFinderSuccess(rutas);
    }

    // Línea de Ruta
    private List<LatLng> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d, lng / 100000d
            ));
        }

        return decoded;
    }
}
