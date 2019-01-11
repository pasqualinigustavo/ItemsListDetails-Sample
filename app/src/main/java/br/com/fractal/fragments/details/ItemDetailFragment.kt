@file:JvmName("ItemDetailFragment")

package br.com.fractal.fragments.details

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.fractal.R
import br.com.fractal.activities.main.MainActivity
import br.com.fractal.di.components.MainComponent
import br.com.fractal.fragments.details.di.DaggerItemDetailComponent
import br.com.fractal.fragments.details.di.ItemDetailModule
import br.com.fractal.model.Beer
import br.com.fractal.utils.AlertUtils
import kotlinx.android.synthetic.main.fragment_item_details.*
import javax.inject.Inject

/**
 * @author Gustavo Pasqualini
 */

class ItemDetailFragment : Fragment(), ItemDetailView {

    companion object {
        val TAG = ItemDetailFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(beer: Beer) = ItemDetailFragment()
    }

    private var hasContext = false
    lateinit var beer: Beer
    @Inject
    lateinit var presenter: ItemDetailPresenter

    private fun tryInjection(context: Context?) {
        if (!hasContext && context != null) {
            hasContext = true
            injectComponents()
        }
    }

    fun injectComponents() {
        DaggerItemDetailComponent.builder()
            .mainComponent(getMainComponent())
            .itemDetailModule(ItemDetailModule())
            .build()
            .inject(this)
    }

    fun getMainComponent(): MainComponent? {
        val activity = activity
        return if (activity is MainActivity) {
            (activity as MainActivity).component
        } else null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_item_details, container, false)
        initComponent(rootView, savedInstanceState)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        tryInjection(activity)
        super.onCreate(savedInstanceState)
    }

    fun initListeners() {
        fragment_item_details__imageview_favorite.setOnClickListener({
            if (presenter.isFavorite(beer))
                presenter.removeFavorite(beer)
            else presenter.addFavorite(beer)
        })
    }

    fun initData() {
        beer.let {
            fragment_item_details__textview_name.setText(beer?.name)
            fragment_item_details__textview_name.setText(beer?.description)
            if (presenter.isFavorite(beer))
                fragment_item_details__imageview_favorite.setImageResource(R.drawable.heart_yellow)
            else fragment_item_details__imageview_favorite.setImageResource(R.drawable.ic_heart_grey600_36dp)
        }
    }

    fun initComponent(view: View?, savedInstanceState: Bundle?) {
        Log.d(TAG, "initComponents")
        presenter.attachView(this)
    }

    override fun showError(message: String) {
        AlertUtils.showMessage(context!!, message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun onDetach() {
        super.onDetach()
        hasContext = false
    }

    override fun setFavorite(isFavorite: Boolean) {
        if (isFavorite)
            fragment_item_details__imageview_favorite.setImageResource(R.drawable.heart_yellow)
        else
            fragment_item_details__imageview_favorite.setImageResource(R.drawable.ic_heart_grey600_36dp)
    }
}