package logic;

import data.model.Incidencia;

import java.util.List;

public interface UpdateTrafficEvents {
    void onUpdateTrafficEventsStart();
    void onUpdateTrafficEventsSuccess(final List<Incidencia> incidences);
    void onUpdateTrafficEventsFailure();
}

