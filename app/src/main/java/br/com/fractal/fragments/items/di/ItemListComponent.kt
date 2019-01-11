package br.com.fractal.fragments.items.di

import br.com.fractal.di.PerFragment
import br.com.fractal.di.components.MainComponent
import br.com.fractal.fragments.items.ItemListFragment
import dagger.Component

@PerFragment
@Component(modules = [(ItemListModule::class)], dependencies = [(MainComponent::class)])
interface ItemListComponent {

    fun inject(target: ItemListFragment)
}