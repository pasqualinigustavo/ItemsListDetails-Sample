package br.com.fractal.activities.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.fractal.R
import br.com.fractal.activities.main.di.MainModule
import br.com.fractal.application.FractalApplication
import br.com.fractal.di.components.ApplicationComponent
import br.com.fractal.di.components.DaggerMainComponent
import br.com.fractal.di.components.MainComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    protected val appComponent: ApplicationComponent
        get() = (application as FractalApplication).applicationComponent

    @Inject
    lateinit var presenter: MainPresenter

    val component: MainComponent by lazy {
        DaggerMainComponent.builder()
            .parent(appComponent)
            .module(MainModule())
            .target(this)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)

        initComponents()
    }

    fun initComponents() {
        component.inject(this)
        presenter.bindView(this)

        initData()
    }


    fun initData() {
        presenter.showItemListView()
        setTitle(getString(R.string.app_name))
    }
}
