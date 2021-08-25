package vic.sample.chatuisample.ui.basic

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import vic.sample.chatuisample.mvvm.model.cache.CacheRepository


open class BasicFragment : Fragment() {

    protected lateinit var mContext: Context
    protected lateinit var mToast: Toast
    var mPageIsLive = false

    val mCache: CacheRepository by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initParams()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPageIsLive = false
    }

    private fun initParams() {
        mPageIsLive = true
        mContext = requireActivity()
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT)
    }

    fun callToast(str: String, isLong: Boolean) {
        mToast.cancel()

        mToast =
            Toast.makeText(mContext, str, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)

        mToast.show()
    }

    fun Fragment.onHideKeyboard() {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0);
    }
}