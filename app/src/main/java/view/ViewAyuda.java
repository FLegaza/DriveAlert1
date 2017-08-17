package view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.project.francisco.drivealert.R;

public class ViewAyuda extends AppCompatActivity {

    public TextView tvAyuda;
    public TextView tvAyudaDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        tvAyuda = (TextView) findViewById(R.id.tvHelp);
        tvAyudaDescripcion = (TextView) findViewById(R.id.tvHelpDescription);
    }
}
