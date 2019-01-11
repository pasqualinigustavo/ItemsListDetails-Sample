package br.com.fractal.activities.launcher.router

class LauncherActivityRouter(private val navigator: LauncherNavigator) : LauncherRouter {

    override fun openMainActivity() {
        navigator.openMainActivity()
    }
}