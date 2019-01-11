package br.com.fractal.activities.launcher

import br.com.fractal.activities.launcher.router.LauncherRouter

class LauncherPresenter(
    private val router: LauncherRouter,
    private val interactor: LauncherInteractor
) {

    private companion object {
        val TAG: String = LauncherPresenter::class.java.simpleName
    }

    var mView: LauncherView? = null

    fun bindView(view: LauncherView) {
        this.mView = view
    }

    fun unbindView() {
        this.mView = null
    }

    fun openMainActivity() {
        router.openMainActivity()
    }
}