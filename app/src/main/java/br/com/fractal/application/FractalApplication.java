package br.com.fractal.application;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import br.com.fractal.di.ApplicationDependency;
import br.com.fractal.di.components.ApplicationComponent;
import br.com.fractal.di.components.ComponentHolder;

public class FractalApplication extends MultiDexApplication {

    private static Context context = null;
    public static final String TAG = "FractalApplication";
    public static ApplicationComponent graph;

    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        ComponentHolder holder = ComponentHolder.INSTANCE;
        graph.inject(holder);
        FractalApplication.context = getApplicationContext();
    }

    public static Context getContext() {
        return FractalApplication.context;
    }

    private void initializeInjector() {
        graph = new ApplicationDependency().getApplicationComponent(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return graph;
    }
}