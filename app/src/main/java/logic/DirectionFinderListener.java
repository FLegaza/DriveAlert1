package logic;

import data.Ruta;
import java.util.List;

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Ruta> rutas);
}

