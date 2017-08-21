package logic;

import java.util.List;

import data.model.Incidencia;
import data.model.Ruta;

public interface GetIncidenciasListener {
    void onGetIncidenciasStart();
    void onGetIncidenciasSuccess(final List<Incidencia> incidencias);
}

