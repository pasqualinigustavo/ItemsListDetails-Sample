package br.com.fractal.fragments.details

import br.com.fractal.model.Beer

interface ItemDetailView {
    fun showError(message: String)
    fun setFavorite(isFavorite: Boolean)
}