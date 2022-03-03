package vic.sample.chatuisample.custom

import android.content.Context
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout

class SlideConstraintLayout : ConstraintLayout {

    private var yFraction = 0f
    private var xFraction = 0f
    private var preDrawListener: ViewTreeObserver.OnPreDrawListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    fun setYFraction(fraction: Float) {
        yFraction = fraction
        if (height == 0) {
            if (preDrawListener == null) {
                preDrawListener = ViewTreeObserver.OnPreDrawListener {
                    viewTreeObserver.removeOnPreDrawListener(preDrawListener)
                    setYFraction(yFraction)
                    true
                }
                viewTreeObserver.addOnPreDrawListener(preDrawListener)
            }
            return
        }
        val translationY = height * fraction
        setTranslationY(translationY)
    }

    fun setXFraction(fraction: Float) {
        xFraction = fraction
        if (height == 0) {
            if (preDrawListener == null) {
                preDrawListener = ViewTreeObserver.OnPreDrawListener {
                    viewTreeObserver.removeOnPreDrawListener(preDrawListener)
                    setXFraction(xFraction)
                    true
                }
                viewTreeObserver.addOnPreDrawListener(preDrawListener)
            }
            return
        }
        val translationX = width * fraction
        setTranslationX(translationX)
    }

    fun getYFraction(): Float {
        return yFraction
    }

    fun getXFraction(): Float {
        return xFraction
    }
}