package br.com.fractal.di.components

import br.com.fractal.activities.main.MainActivity
import br.com.fractal.activities.main.di.MainModule
import br.com.fractal.di.PerActivity
import dagger.BindsInstance
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(MainModule::class))
interface MainComponent {

    fun inject(item: MainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun target(activity: MainActivity): Builder

        fun module(module: MainModule): Builder
        fun parent(applicationComponent: ApplicationComponent): Builder
        fun build(): MainComponent
    }
}
