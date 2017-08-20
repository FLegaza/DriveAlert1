package logic;

import android.util.JsonReader;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import data.model.Incidencia;
import data.model.Ruta;

public class ParserJSON {

    // Clase para aislar el Leer un JSON del Direction Finder, y usarla en las incidencias.

    // -------- GSON -------- (OPCIONAL)

    // Parser JSON para la RUTA - GSON (Se ha añadido el 'compile' en el gradle y la librería)
    public List<Ruta> leerFlujoJsonRuta(InputStream in) throws IOException {

        // Nueva instancia de la clase Gson
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        List<Ruta> rutas = new ArrayList<>();

        // Iniciar el array
        reader.beginArray();
        while (reader.hasNext()) {
            // Lectura de objetos
            Ruta ruta = gson.fromJson(String.valueOf(reader), Ruta.class); // Era sin valueOf, para corregir el error lo he puesto.
            rutas.add(ruta);
        }
        reader.endArray();
        reader.close();
        return rutas;
    }

    // ------- JSON -------

    // Parser JSON para las INCIDENCIAS
    public List<Incidencia> leerFlujoJsonIncidencia(InputStream in) throws IOException {
        // Nueva instancia JsonReader
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            // Leer Array
            return leerArrayIncidencias(reader);
        } finally {
            reader.close();
        }
    }

    public List<Incidencia> leerArrayIncidencias(JsonReader reader) throws IOException {
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

    public Incidencia leerIncidencia(JsonReader reader) throws IOException {
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
