package br.com.fractal.activities.main.router

import android.support.v4.app.Fragment
import android.util.Log
import br.com.fractal.R
import br.com.fractal.activities.main.MainActivity
import br.com.fractal.fragments.items.ItemListFragment

class MainNavigator(private val activity: MainActivity) {

    companion object {
        private val TAG = MainNavigator::class.java.simpleName
    }

    fun showItemListView() {
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

    public fun clearBackStack() {
        val fm = activity.supportFragmentManager
        val count = fm.backStackEntryCount
        (0 until count).forEach { _ -> fm.popBackStack() }
    }

    fun closeApp() {
        activity.finish()
    }
}