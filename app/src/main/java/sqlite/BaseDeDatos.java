package sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;


public class BaseDeDatos extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DATOS = "DriveAlertBD.db";
    private static final int VERSION_ACTUAL = 1;
    private final Context contexto;

    String SQLCreateTableRuta = "CREATE TABLE ruta (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "origen STRING, destino STRING, origen INTEGER," +
            "latorigen REAL, lngorigen REAL, " +
            "latdestino REAL, lngdestino REAL," +
            "distancia STRING, distanciaint INTEGER," +
            "duracion STRING, durancionint INTEGER," +
            "puntosruta STRING, incidencias STRING);";

    String SQLCreateTableIncidencia = "CREATE TABLE incidencia (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "carretera STRING, estado INTEGER, alias STRING," +
            "sentido STRING, pk REAL, tipo STRING," +
            "lng REAL, codele STRING, lat REAL, " +
            "precision STRING, fecha STRING, poblacion STRING," +
            "descripcion STRING, fechafin STRING, tipoinci STRING," +
            "pkfinal INTEGER, provincia STRING, causa STRING, hora STRING," +
            "pkini INTEGER, icono STRING, horafin STRING, nivel STRING);";

    String SQLCreateTableParam = "CREATE TABLE param (" +
            "rutacoche BOOLEAN, rutapie BOOLEAN, rutabici BOOLEAN," +
            "rutapublic BOOLEAN, publicbus BOOLEAN, publictren BOOLEAN," +
            "peaje BOOLEAN, autovia BOOLEAN, ferry BOOLEAN," +
            "incicam BOOLEAN, incisensor BOOLEAN, inciradar BOOLEAN," +
            "incireten BOOLEAN, inciobra BOOLEAN);";

       // Creamos la Base de Datos
    public BaseDeDatos(Context contexto) {
        super(contexto, NOMBRE_BASE_DATOS, null, VERSION_ACTUAL);
        this.contexto = contexto;
    }

    // Se crean las tablas de la Base de Datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLCreateTableRuta);
        db.execSQL(SQLCreateTableIncidencia);
        db.execSQL(SQLCreateTableParam);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
