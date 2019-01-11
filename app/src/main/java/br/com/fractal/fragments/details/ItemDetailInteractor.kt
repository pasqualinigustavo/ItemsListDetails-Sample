package br.com.fractal.fragments.details

import br.com.fractal.database.DaoFactory
import br.com.fractal.model.Beer
import br.com.fractal.rest.ApiComm

class ItemDetailInteractor(
    private val apiComm: ApiComm,
    private val daoFactory: DaoFactory
) {

    private companion object {
        val TAG: String = ItemDetailInteractor::class.java.simpleName
    }

    fun addFavorite(beer: Beer) {
        DaoFactory.getInstance().beerDao.insertEntity(beer)
    }

    fun removeFavorite(beer: Beer) {
        DaoFactory.getInstance().beerDao.deleteEntity(beer)
    }

    fun isFavorite(beer: Beer): Boolean {
        return DaoFactory.getInstance().beerDao.isFavorite(beer.id)
    }
}