package br.com.fractal.fragments.details.di

import br.com.fractal.di.PerFragment
import br.com.fractal.di.components.MainComponent
import br.com.fractal.fragments.details.ItemDetailFragment
import dagger.Component

@PerFragment
@Component(modules = [(ItemDetailModule::class)], dependencies = [(MainComponent::class)])
interface ItemDetailComponent {

    fun inject(target: ItemDetailFragment)
}