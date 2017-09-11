package view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.project.francisco.drivealert.R;

import data.repository.RouteRepository;
import di.library.BaseActivity;
import view.adapter.ShowRoutesAdapter;

/**
 * Created by miguelangel on 10/9/17.
 */

public class RutasGuardadas extends BaseActivity {

    public TextView tableTitle;
    RecyclerView routesRecycler;

    private RouteRepository repository = injector.getRouteRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_routes);

        routesRecycler = (RecyclerView) findViewById(R.id.routes_recycler);
        tableTitle = (TextView) findViewById(R.id.routes_title);

        loadRoutesTable();
    }

    private void loadRoutesTable() {
        LinearLayoutManager manager  = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        final ShowRoutesAdapter adapter = new ShowRoutesAdapter(repository.getListRoutes());
        routesRecycler.setLayoutManager(manager);
        routesRecycler.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }
}
