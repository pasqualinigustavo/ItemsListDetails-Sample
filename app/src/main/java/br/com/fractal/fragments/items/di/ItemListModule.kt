package br.com.fractal.fragments.items.di

import br.com.fractal.activities.main.router.MainNavigator
import br.com.fractal.database.DaoFactory
import br.com.fractal.di.PerFragment
import br.com.fractal.fragments.items.ItemListInteractor
import br.com.fractal.fragments.items.ItemListPresenter
import br.com.fractal.fragments.items.router.ItemListActivityRouter
import br.com.fractal.fragments.items.router.ItemListRouter
import br.com.fractal.rest.ApiComm
import dagger.Module
import dagger.Provides

@Module
class ItemListModule {

    @Provides
    @PerFragment
    fun presenter(interactor: ItemListInteractor, router: ItemListRouter) =
        ItemListPresenter(interactor, router)

    @Provides
    @PerFragment
    fun interactor(apiComm: ApiComm, daoFactory: DaoFactory) = ItemListInteractor(apiComm, daoFactory)

    @Provides
    @PerFragment
    fun router(navigator: MainNavigator): ItemListRouter =
        ItemListActivityRouter(navigator)
}