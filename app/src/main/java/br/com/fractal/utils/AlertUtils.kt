package br.com.fractal.utils

import android.content.Context
import android.support.annotation.StringRes
import br.com.fractal.R
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme

object AlertUtils {

    /**
     * Exibe alerta com uma mensagem informativa.
     */
    fun showMessage(context: Context, @StringRes message: Int) {
        AlertUtils.showMessage(context, context.getString(message))
    }

    /**
     * Exibe alerta com uma mensagem informativa.
     */
    fun showMessage(context: Context, message: String) {
        AlertUtils.showDialog(context, context.getString(R.string.label_attention), message)
    }

    /**
     * Exibe uma tela com mensagem de aviso.
     */
    fun showWarning(context: Context, @StringRes message: Int) {
        AlertUtils.showDialog(context, context.getString(R.string.label_attention), context.getString(message))
    }

    /**
     * Exibe uma tela com mensagem de erro.
     */
    fun showError(context: Context, @StringRes message: Int) {
        AlertUtils.showDialog(context, context.getString(R.string.label_attention), context.getString(message))
    }

    private fun showDialog(context: Context?, title: String, message: String) {
        context.let {
            val b = MaterialDialog.Builder(context!!)
            b.title(title)
            b.cancelable(false)
            b.theme(Theme.LIGHT)
            b.content(message)
            b.positiveText(context.getString(R.string.label_ok))
            b.onPositive { dialog, which -> dialog.dismiss() }
            b.show()
        }
    }
}
