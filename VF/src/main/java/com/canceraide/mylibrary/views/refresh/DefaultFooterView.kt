package com.canceraide.mylibrary.views.refresh

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.animation.Animation
import android.widget.LinearLayout
import com.canceraide.mylibrary.R
import kotlinx.android.synthetic.main.view_defaultheaderview.view.*

/**
 * Created by mr.lin on 2018/1/15.
 * 默认FooterView
 */
class DefaultFooterView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : AttachView(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private var footerHeight = viewHeight

    private lateinit var rotateAnimator1: ObjectAnimator
    private lateinit var rotateAnimator2: ObjectAnimator
    private lateinit var rotateAnimator3: ObjectAnimator
    private lateinit var rotateAnimator4: ObjectAnimator
    private lateinit var animatorSet: AnimatorSet

    private var valueAnimator: ValueAnimator = ValueAnimator()

    init {
        LayoutInflater.from(context).inflate(R.layout.view_defaultfooterview, this)

        val params = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0)
        params.gravity = Gravity.CENTER
        layoutParams = params
    }

    override fun setVisibleHeight(height: Float) {
        var offset = height
        if (offset > width) {
            offset = width.toFloat()
        }
        val params = layoutParams
        params.height = (offset * 0.4).toInt()//阻尼
        layoutParams = params
        onAttachViewHeightChangeListener?.onHeightChange((offset * 0.4).toInt())
    }

    override fun isEnought(): Boolean {
        val currentHeight = layoutParams.height
        return currentHeight >= footerHeight / 2
    }

    override fun start() {
        changeHeight(layoutParams.height, footerHeight, {}, { })
    }

    override fun end() {
        changeHeight(layoutParams.height, 0, { }, {})
    }

    override fun cancel() {
        changeHeight(layoutParams.height, 0, {}, {})
    }

    private fun changeHeight(currentHeight: Int, target: Int, start: () -> Unit, end: () -> Unit) {
        if (valueAnimator.isRunning) {
            valueAnimator.cancel()
        }
        valueAnimator = ValueAnimator.ofInt(currentHeight, target)
        valueAnimator.duration = 500
        valueAnimator.addUpdateListener {
            val height = valueAnimator.animatedValue as Int
            val params = layoutParams
            params.height = height
            layoutParams = params
            onAttachViewHeightChangeListener?.onHeightChange(height)
        }
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
                start()
            }

            override fun onAnimationEnd(animation: Animator?) {
                end()
            }
        })
        valueAnimator.start()
    }

}