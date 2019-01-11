package br.com.fractal.fragments.items

import android.util.Log
import br.com.fractal.R
import br.com.fractal.application.FractalApplication
import br.com.fractal.fragments.items.router.ItemListRouter
import br.com.fractal.model.Beer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Gustavo Pasqualini
 */

class ItemListPresenter constructor(private val interactor: ItemListInteractor) {

    companion object {
        val TAG = ItemListPresenter::class.java.simpleName
    }

    private var view: ItemListView? = null

    fun attachView(view: ItemListView) {
        this.view = view
        Log.d(TAG, "attachView")
    }

    fun detachView() {
        this.view = null
    }

    private fun onError(error: Throwable) {
        Log.e(TAG, error.message)
    }

    fun getBeers(currentDataSetPage: Int) {
        Log.d(TAG, "getBeers")
        interactor.beersList(currentDataSetPage)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                if (response != null && response != null) {
                    view?.showBeers(response?.data)
                } else if (response == null) {
                    view?.showError(FractalApplication.getContext().getString(R.string.error_communication))
                }
            }, { error ->
                view?.showError(FractalApplication.getContext().getString(R.string.error_communication))
            })
    }

    fun showItemDetails(item: Beer) {

    }
}