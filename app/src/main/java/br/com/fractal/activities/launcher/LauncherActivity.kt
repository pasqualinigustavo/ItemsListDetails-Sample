package br.com.fractal.activities.launcher

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import br.com.fractal.R
import br.com.fractal.activities.launcher.di.DaggerLauncherComponent
import br.com.fractal.activities.launcher.di.LauncherComponent
import br.com.fractal.activities.launcher.di.LauncherModule
import br.com.fractal.application.FractalApplication
import br.com.fractal.di.components.ApplicationComponent
import br.com.fractal.utils.AlertUtils
import javax.inject.Inject

class LauncherActivity : AppCompatActivity(), LauncherView {

    protected val appComponent: ApplicationComponent
        get() = (application as FractalApplication).applicationComponent

    companion object {
        val TAG: String = LauncherActivity::class.java.simpleName
    }

    @Inject
    lateinit var presenter: LauncherPresenter

    val component: LauncherComponent by lazy {
        DaggerLauncherComponent.builder()
            .parent(appComponent)
            .module(LauncherModule())
            .target(this)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_launcher)

        initComponents()
    }

    fun initComponents() {
        Log.d(TAG, "initComponents")
        component.inject(this)
        presenter.bindView(this)
        initData()
    }

    fun initData() {
        presenter.openMainActivity()
    }

    override fun showError(message: String) {
        AlertUtils.showMessage(this, message)
    }
}