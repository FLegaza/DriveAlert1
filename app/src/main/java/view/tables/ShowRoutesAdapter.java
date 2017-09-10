package view.tables;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        View itemView = inflater.inflate(R.layout.viewholder_route, parent, false);

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

    public RouteViewHolder(View itemView) {
        super(itemView);
    }

    public void render(Ruta route) {

    }
}
