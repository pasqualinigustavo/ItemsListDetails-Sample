package br.com.fractal.activities.main.router

class MainActivityRouter(private val navigator: MainNavigator) : MainRouter {

    override fun showMapView() {
        navigator.showMapView()
    }

    override fun closeApp() = navigator.closeApp()
}