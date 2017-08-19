package logic;

import data.Ruta;
import java.util.List;

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(final List<Ruta> rutas);
}

