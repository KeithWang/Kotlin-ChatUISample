package vic.sample.chatuisample.ui.basic

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import vic.sample.chatuisample.mvvm.model.cache.CacheRepository
import vic.sample.chatuisample.utility.Tools

open class BasicActivity : AppCompatActivity() {

    private lateinit var mToast: Toast
    lateinit var mContext: Context
    lateinit var dialog: Dialog

    val mCache: CacheRepository by inject()
    val mTools: Tools by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParams()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initParams() {
        mContext = this
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT)
    }

    fun callToast(str: String, isLong: Boolean) {
        mToast.cancel()

        mToast =
            Toast.makeText(mContext, str, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)

        mToast.show()
    }

    fun Activity.onHideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0);
    }
}