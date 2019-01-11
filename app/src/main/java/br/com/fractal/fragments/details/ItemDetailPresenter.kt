package br.com.fractal.fragments.details

import android.util.Log
import br.com.fractal.model.Beer

/**
 * @author Gustavo Pasqualini
 */

class ItemDetailPresenter constructor(private val interactor: ItemDetailInteractor) {

    companion object {
        val TAG = ItemDetailPresenter::class.java.simpleName
    }

    private var view: ItemDetailView? = null

    fun attachView(view: ItemDetailView) {
        this.view = view
        Log.d(TAG, "attachView")
    }

    fun detachView() {
        this.view = null
    }

    private fun onError(error: Throwable) {
        Log.e(TAG, error.message)
    }

    fun addFavorite(beer: Beer) {
        interactor.addFavorite(beer)
        view?.setFavorite(true)
    }

    fun removeFavorite(beer: Beer) {
        interactor.removeFavorite(beer)
        view?.setFavorite(false)
    }

    fun isFavorite(beer: Beer): Boolean {
        return interactor.isFavorite(beer)
    }
}