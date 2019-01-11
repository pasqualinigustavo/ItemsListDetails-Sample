package br.com.fractal.fragments.items.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by pasqualini on 15/01/16.
 */
public interface IAdapterDataSource {
    void adapterUserWantsLoadMoreData(RecyclerView.Adapter apadter);
}
