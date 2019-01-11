package br.com.fractal.fragments.items.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import br.com.fractal.R
import br.com.fractal.database.DaoFactory
import br.com.fractal.model.Beer

class BeerAdapter(private var dataSet: MutableList<Beer>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private val VIEW_TYPE_ITEM = 0
        private val VIEW_TYPE_LOADING = 1
    }

    private var itemClickListener: OnItemClickListener? = null
    private var adapterInteractionListener: IAdapterDataSource? = null
    private var hasMoreData = true
    private var loadProgressBar: ProgressBar? = null

    val itemList: List<Beer>?
        get() = this.dataSet

    interface OnItemClickListener {

        fun onItemClickInfo(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_beer, parent, false)
            return ViewHolder(view)
        } else if (viewType == VIEW_TYPE_LOADING) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_load_more, parent, false)
            return LoadViewHolder(view)
        }
        return null
    }

    override fun getItemViewType(position: Int): Int {
        if (dataSet != null) {
            if (dataSet!!.size > 0 && position != dataSet!!.size)
                return VIEW_TYPE_ITEM
            else if (dataSet!!.size > 0 && dataSet!!.size == position && hasMoreData)
                return VIEW_TYPE_LOADING
        }
        return VIEW_TYPE_LOADING
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is LoadViewHolder) {
            if (adapterInteractionListener != null) {
                val visibility = if (hasMoreData) View.VISIBLE else View.GONE

                viewHolder.progressBar.visibility = visibility

                userWantsLoadMore(viewHolder.progressBar)
            }
        } else if (viewHolder is ViewHolder) {
            var entity: Beer = this.dataSet!![position]
            //name
            viewHolder.row_beer__textview_name.text = entity.name
            //desc
            viewHolder.row_beer__textview_desc.text = entity.description

            if (DaoFactory.getInstance().beerDao.isFavorite(entity.id)) {
                viewHolder.row_beer__imageview_favorite.setImageResource(R.drawable.heart_yellow)
            } else viewHolder.row_beer__imageview_favorite.setImageResource(R.drawable.ic_heart_grey600_36dp)
        }
    }

    override fun getItemCount(): Int {
        return if (dataSet != null && dataSet!!.size > 0) dataSet!!.size + 1 else 0

    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun getItem(position: Int): Beer {
        return this.dataSet!![position]
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val row_beer__textview_name: TextView
        val row_beer__textview_desc: TextView
        val row_beer__imageview_favorite: ImageView
        val row_beer__container: View

        val view: View
            get() = itemView

        init {
            row_beer__container = view.findViewById(R.id.row_beer__container)
            row_beer__textview_name = view.findViewById(R.id.row_beer__textview_name)
            row_beer__textview_desc = view.findViewById(R.id.row_beer__textview_desc)
            row_beer__imageview_favorite = view.findViewById(R.id.row_beer__imageview_favorite)
            row_beer__container.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if (itemClickListener != null)
                itemClickListener?.onItemClickInfo(v, adapterPosition)
        }

    }

    internal class LoadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar

        init {
            progressBar = itemView.findViewById<View>(R.id.row_load_more_progress_bar) as ProgressBar
        }
    }

    fun setAdapterInteractionListener(adapterInteractionListener: IAdapterDataSource) {
        this.adapterInteractionListener = adapterInteractionListener
    }

    fun setHasMoreData(hasMoreData: Boolean) {
        this.hasMoreData = hasMoreData

        if (loadProgressBar != null) {
            val visibility = if (hasMoreData) View.VISIBLE else View.GONE
            loadProgressBar!!.visibility = visibility
        }
    }

    private fun userWantsLoadMore(progressBar: ProgressBar) {
        loadProgressBar = progressBar
        if (hasMoreData && adapterInteractionListener != null) {
            loadProgressBar!!.visibility = View.VISIBLE
            adapterInteractionListener!!.adapterUserWantsLoadMoreData(this)
        }
    }

    fun addMoreItemsToDateSet(items: List<Beer>?) {
        if (items != null && items.size > 0) {
            for (i in items.indices) {
                if (!dataSet!!.contains(items[i]))
                    dataSet!!.add(items[i])
            }
        }
        notifyDataSetChanged()
    }
}
