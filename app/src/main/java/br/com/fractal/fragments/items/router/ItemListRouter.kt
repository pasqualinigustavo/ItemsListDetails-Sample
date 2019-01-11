package br.com.fractal.fragments.items.router

import br.com.fractal.model.Beer

interface ItemListRouter {
    fun showItemDetailView(beer: Beer)
}