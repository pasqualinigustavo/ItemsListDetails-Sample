package br.com.fractal.di.components

import android.app.Activity

import br.com.fractal.di.PerActivity
import br.com.fractal.di.modules.ActivityModule

import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun activity(): Activity
}
