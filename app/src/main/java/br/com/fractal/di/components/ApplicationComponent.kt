package br.com.fractal.di.components

import android.content.Context
import br.com.fractal.di.modules.ApiModule
import br.com.fractal.di.modules.ApplicationDIModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationDIModule::class, ApiModule::class])
interface ApplicationComponent {

    fun context(): Context

    fun inject(holder: ComponentHolder)
}
