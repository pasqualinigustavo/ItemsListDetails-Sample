package br.com.fractal.fragments.items.router

import android.support.v4.app.Fragment
import android.util.Log
import br.com.fractal.R
import br.com.fractal.activities.main.MainActivity
import br.com.fractal.fragments.items.ItemListFragment

class ItemListNavigator(private val activity: MainActivity) {

    companion object {
        private val TAG = ItemListNavigator::class.java.simpleName
    }

    fun showItemDetailView() {
        setFragment(ItemListFragment.newInstance(),ItemListFragment.Companion.TAG)
    }

    private fun setFragment(fragment: Fragment, tag: String) {
        try {
            activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.activity_content, fragment)
                    .addToBackStack(tag)
                    .commit()
            activity.supportFragmentManager.executePendingTransactions()
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }
}