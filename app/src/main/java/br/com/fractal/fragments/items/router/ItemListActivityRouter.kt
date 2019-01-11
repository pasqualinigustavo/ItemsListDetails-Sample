package br.com.fractal.fragments.items.router

import br.com.fractal.activities.main.router.MainNavigator
import br.com.fractal.model.Beer

class ItemListActivityRouter(private val navigator: MainNavigator) : ItemListRouter {

    override fun showItemDetailView(beer: Beer) = navigator.showItemDetailView(beer)
}