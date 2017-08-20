package di.library;

import android.support.v7.app.AppCompatActivity;

import di.Injector;
import di.InjectorImpl;

/**
 * Created by miguelangel on 20/8/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public Injector injector = InjectorImpl.getInstance();
}
