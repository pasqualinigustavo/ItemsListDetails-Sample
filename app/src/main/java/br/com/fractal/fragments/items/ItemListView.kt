package br.com.fractal.fragments.items

import br.com.fractal.model.Beer

interface ItemListView {
    fun showError(message: String)
    fun showBeers(beerList: MutableList<Beer>?)
}