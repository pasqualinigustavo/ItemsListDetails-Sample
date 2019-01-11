package br.com.fractal.fragments.details.di

import br.com.fractal.database.DaoFactory
import br.com.fractal.di.PerFragment
import br.com.fractal.fragments.details.ItemDetailInteractor
import br.com.fractal.fragments.details.ItemDetailPresenter
import br.com.fractal.rest.ApiComm
import dagger.Module
import dagger.Provides

@Module
class ItemDetailModule {
    @Provides
    @PerFragment
    fun presenter(interactor: ItemDetailInteractor) = ItemDetailPresenter(interactor)

    @Provides
    @PerFragment
    fun interactor(apiComm: ApiComm, daoFactory: DaoFactory) = ItemDetailInteractor(apiComm, daoFactory)
}