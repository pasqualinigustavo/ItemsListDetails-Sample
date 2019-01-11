package br.com.fractal.activities.main.router

class MainActivityRouter(private val navigator: MainNavigator) : MainRouter {

    override fun closeApp() = navigator.closeApp()
    override fun showItemListView() = navigator.showItemListView()
}