package br.com.fractal.activities.launcher.router

import android.content.Intent
import br.com.fractal.activities.launcher.LauncherActivity
import br.com.fractal.activities.main.MainActivity

class LauncherNavigator(private val activity: LauncherActivity) {

    companion object {
        private val TAG = LauncherNavigator::class.java.simpleName
    }

    fun openMainActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME)
        activity.startActivity(intent)
        activity.finish()
    }
}