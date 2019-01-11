package br.com.fractal.fragments.items.router

class ItemListActivityRouter(private val navigator: ItemListNavigator) : ItemListRouter {

    override fun showItemDetailView() = navigator.showItemDetailView()
}