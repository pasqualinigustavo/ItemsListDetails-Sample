package br.com.fractal.di.modules

import android.content.Context
import br.com.fractal.application.FractalApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationDIModule(private val application: FractalApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return application
    }
}