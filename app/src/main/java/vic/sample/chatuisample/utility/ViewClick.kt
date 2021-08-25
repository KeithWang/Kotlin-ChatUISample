package vic.sample.chatuisample.utility

import android.view.View

abstract class ViewClick() : View.OnClickListener {

    constructor(limit: Int) : this() {
        mLimit = limit
    }

    private var mLimit = 500

    private var mFastClickReturn: Long = 0

    private fun fastClickCheck(): Boolean {
        if (System.currentTimeMillis() - mFastClickReturn < mLimit) {
            return true
        }
        mFastClickReturn = System.currentTimeMillis()
        return false
    }

    override fun onClick(view: View?) {
        if (fastClickCheck() || view == null)
            return
        CustomOnClick(view)
    }

    protected abstract fun CustomOnClick(view: View)

}