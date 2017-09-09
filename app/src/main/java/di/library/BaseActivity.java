package di.library;

import android.support.v7.app.AppCompatActivity;

import di.Injector;
import di.InjectorImpl;

public abstract class BaseActivity extends AppCompatActivity {

    public Injector injector = InjectorImpl.getInstance();
}
