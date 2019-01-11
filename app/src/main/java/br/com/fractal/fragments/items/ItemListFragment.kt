@file:JvmName("ItemListFragment")

package br.com.fractal.fragments.items

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.fractal.R
import br.com.fractal.activities.main.MainActivity
import br.com.fractal.activities.main.di.MainModule
import br.com.fractal.di.components.DaggerMainComponent
import br.com.fractal.di.components.MainComponent
import br.com.fractal.fragments.items.adapter.BeerAdapter
import br.com.fractal.fragments.items.adapter.IAdapterDataSource
import br.com.fractal.fragments.items.di.DaggerItemListComponent
import br.com.fractal.fragments.items.di.ItemListModule
import br.com.fractal.model.Beer
import br.com.fractal.utils.AlertUtils
import kotlinx.android.synthetic.main.fragment_items.*
import javax.inject.Inject


/**
 * @author Gustavo Pasqualini
 */

class ItemListFragment : Fragment(), ItemListView, IAdapterDataSource {

    companion object {
        val TAG = ItemListFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() = ItemListFragment()
    }

    private var overallYScroll: Int = 0
    private var hasContext = false
    lateinit var adapter: BeerAdapter
    @Inject
    lateinit var presenter: ItemListPresenter
    private var currentDataSetPage = 1

    private fun tryInjection(context: Context?) {
        if (!hasContext && context != null) {
            hasContext = true
            injectComponents()
        }
    }

    fun injectComponents() {
        DaggerItemListComponent.builder()
            .mainComponent(getMainComponent())
            .itemListModule(ItemListModule())
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
        val rootView = inflater.inflate(R.layout.fragment_items, container, false)
        initComponent(rootView, savedInstanceState)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lm = LinearLayoutManager(activity)
        lm.orientation = LinearLayoutManager.VERTICAL
        fragment_beer__recyclerview.layoutManager = lm

        initListeners()

        presenter.getBeers(currentDataSetPage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        tryInjection(activity)
        super.onCreate(savedInstanceState)
    }

    fun initListeners() {
        fragment_beer__recyclerview.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                overallYScroll = lastVisibleItem(recyclerView)
            }
        })
    }

    private fun lastVisibleItem(recyclerView: RecyclerView?): Int {
        var lastItemVisiblePos = 0
        if (recyclerView != null && recyclerView.adapter != null) {
            if (recyclerView.adapter.itemCount != 0) {
                lastItemVisiblePos =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

            }
        }
        return lastItemVisiblePos
    }

    fun initComponent(view: View?, savedInstanceState: Bundle?) {
        Log.d(TAG, "initComponents")
        presenter.attachView(this)
    }

    override fun showBeers(beerList: MutableList<Beer>?) {
        if (currentDataSetPage === 1 || adapter == null) {
            adapter = BeerAdapter(beerList)
            adapter.setAdapterInteractionListener(this)
            adapter.setOnItemClickListener(object : BeerAdapter.OnItemClickListener {
                override fun onItemClickInfo(view: View, position: Int) {
                    presenter.showItemDetails(adapter.getItem(position))
                }
            })

            fragment_beer__recyclerview.setAdapter(adapter)
        } else {
            if (beerList == null || beerList.size == 0)
                adapter.setHasMoreData(false);
            else {
                if (beerList.size < 10)
                    adapter.setHasMoreData(false);
                adapter.addMoreItemsToDateSet(beerList);
            }
            if (currentDataSetPage > 1) {
                fragment_beer__recyclerview.setAdapter(adapter);
                fragment_beer__recyclerview.scrollToPosition(overallYScroll);
            }
        }
    }

    fun teste() {

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

    override fun adapterUserWantsLoadMoreData(apadter: RecyclerView.Adapter<*>) {
        currentDataSetPage++
        presenter.getBeers(currentDataSetPage)
    }
}