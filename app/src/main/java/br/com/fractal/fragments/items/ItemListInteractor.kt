package br.com.fractal.fragments.items

import br.com.fractal.database.DaoFactory
import br.com.fractal.rest.ApiComm

class ItemListInteractor(
    private val apiComm: ApiComm,
    private val daoFactory: DaoFactory
) {

    private companion object {
        val TAG: String = ItemListInteractor::class.java.simpleName
    }

    fun beersList(page: Int) = apiComm.searchEndPoint().beersList(page)

}