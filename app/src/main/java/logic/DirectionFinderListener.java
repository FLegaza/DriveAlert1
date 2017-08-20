package logic;

import data.model.Ruta;
import java.util.List;

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(final List<Ruta> rutas);
}

