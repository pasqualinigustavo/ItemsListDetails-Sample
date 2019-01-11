package br.com.fractal.activities.launcher.di

import br.com.fractal.activities.launcher.LauncherActivity
import br.com.fractal.activities.launcher.LauncherInteractor
import br.com.fractal.activities.launcher.LauncherPresenter
import br.com.fractal.activities.launcher.router.LauncherActivityRouter
import br.com.fractal.activities.launcher.router.LauncherNavigator
import br.com.fractal.activities.launcher.router.LauncherRouter
import br.com.fractal.database.DaoFactory
import br.com.fractal.di.PerActivity
import br.com.fractal.rest.ApiComm
import dagger.Module
import dagger.Provides

@Module
class LauncherModule {

    @Provides
    @PerActivity
    fun router(navigator: LauncherNavigator): LauncherRouter = LauncherActivityRouter(navigator)

    @Provides
    @PerActivity
    fun navigator(activity: LauncherActivity) = LauncherNavigator(activity)

    @Provides
    @PerActivity
    fun presenter(router: LauncherRouter, interactor: LauncherInteractor) = LauncherPresenter(router, interactor)

    @Provides
    @PerActivity
    fun interactor(apiComm: ApiComm, daoFactory: DaoFactory) = LauncherInteractor(apiComm, daoFactory)

}