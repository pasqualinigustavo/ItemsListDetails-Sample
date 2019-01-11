package br.com.fractal.activities.launcher.di

import br.com.fractal.activities.launcher.LauncherActivity
import br.com.fractal.di.PerActivity
import br.com.fractal.di.components.ApplicationComponent
import dagger.BindsInstance
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(LauncherModule::class))
interface LauncherComponent {

    fun inject(activity: LauncherActivity)

    @Component.Builder
    interface Builder {
        fun parent(parent: ApplicationComponent): Builder
        fun module(module: LauncherModule): Builder
        @BindsInstance
        fun target(target: LauncherActivity): Builder

        fun build(): LauncherComponent
    }
}
