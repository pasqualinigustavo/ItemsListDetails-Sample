package br.com.fractal.di;

import br.com.fractal.application.FractalApplication;
import br.com.fractal.di.components.ApplicationComponent;
import br.com.fractal.di.components.DaggerApplicationComponent;
import br.com.fractal.di.modules.ApplicationDIModule;


public class ApplicationDependency {

    private ApplicationComponent applicationComponent;

    public ApplicationComponent getApplicationComponent(FractalApplication application) {
        return this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationDIModule(new ApplicationDIModule(application))
                .build();
    }

}


