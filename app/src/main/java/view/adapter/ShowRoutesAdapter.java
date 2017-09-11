package view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.francisco.drivealert.R;

import java.util.List;

import data.model.Ruta;

/**
 * Created by miguelangel on 10/9/17.
 */

public class ShowRoutesAdapter extends RecyclerView.Adapter<RouteViewHolder> {

    private List<Ruta> routes;

    public ShowRoutesAdapter(List<Ruta> routes) {
        this.routes = routes;
    }

    @Override
    public RouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView = inflater.inflate(R.layout.viewholder_route, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(lp);

        return new RouteViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(RouteViewHolder holder, int position) {
        int index = position % routes.size();
        Ruta route = routes.get(index);
        holder.render(route);
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }
}

class RouteViewHolder extends RecyclerView.ViewHolder {

    View itemView;
    TextView information;
    TextView duration;
    TextView distance;
    TextView trafficEvents;

    public RouteViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        this.information = (TextView)itemView.findViewById(R.id.information);
        this.duration = (TextView)itemView.findViewById(R.id.duration);
        this.distance = (TextView)itemView.findViewById(R.id.distance);
        this.trafficEvents = (TextView)itemView.findViewById(R.id.total_trafficEvents);
    }

    public void render(Ruta route) {
        information.setText(route.origen.toUpperCase().split(",")[0]+" - "+route.destino.toUpperCase().split(",")[0]);
        duration.setText(route.duracionStr);
        distance.setText(route.distanciaStr);
        trafficEvents.setText(itemView.getContext().getString(R.string.trafficEventsRow)+" "+String.valueOf(route.IncidenciasRuta.size()));
    }
}
