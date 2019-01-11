package br.com.fractal.activities.main.di

import br.com.fractal.activities.main.MainActivity
import br.com.fractal.activities.main.MainPresenter
import br.com.fractal.activities.main.router.MainActivityRouter
import br.com.fractal.activities.main.router.MainNavigator
import br.com.fractal.activities.main.router.MainRouter
import br.com.fractal.di.PerActivity
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    @PerActivity
    fun router(navigator: MainNavigator): MainRouter = MainActivityRouter(navigator)

    @Provides
    @PerActivity
    fun navigator(activity: MainActivity) = MainNavigator(activity)

    @Provides
    @PerActivity
    fun presenter(router: MainRouter) =
            MainPresenter(router)
}