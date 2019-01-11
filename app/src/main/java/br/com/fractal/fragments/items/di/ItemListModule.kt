package br.com.fractal.fragments.items.di

import br.com.fractal.activities.main.MainActivity
import br.com.fractal.activities.main.router.MainNavigator
import br.com.fractal.database.DaoFactory
import br.com.fractal.di.PerActivity
import br.com.fractal.di.PerFragment
import br.com.fractal.fragments.items.ItemListInteractor
import br.com.fractal.fragments.items.ItemListPresenter
import br.com.fractal.fragments.items.router.ItemListActivityRouter
import br.com.fractal.fragments.items.router.ItemListNavigator
import br.com.fractal.fragments.items.router.ItemListRouter
import br.com.fractal.rest.ApiComm
import dagger.Module
import dagger.Provides

@Module
class ItemListModule {

    @Provides
    @PerFragment
    fun router(navigator: ItemListNavigator): ItemListRouter = ItemListActivityRouter(navigator)

    @Provides
    @PerFragment
    fun navigator(activity: MainActivity) = ItemListNavigator(activity)

    @Provides
    @PerFragment
    fun presenter(interactor: ItemListInteractor) =
        ItemListPresenter(interactor)

    @Provides
    @PerFragment
    fun interactor(apiComm: ApiComm, daoFactory: DaoFactory) = ItemListInteractor(apiComm, daoFactory)
}