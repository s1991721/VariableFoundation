package com.canceraide.mylibrary.views.refresh

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.animation.Animation.INFINITE
import android.widget.LinearLayout
import com.canceraide.mylibrary.R
import kotlinx.android.synthetic.main.view_defaultheaderview.view.*

/**
 * Created by mr.lin on 2018/1/15.
 * 默认HeaderView
 */
class DefaultHeaderView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : AttachView(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private var headerHeight = viewHeight

    private lateinit var rotateAnimator1: ObjectAnimator
    private lateinit var rotateAnimator2: ObjectAnimator
    private lateinit var rotateAnimator3: ObjectAnimator
    private lateinit var rotateAnimator4: ObjectAnimator
    private lateinit var animatorSet: AnimatorSet

    private var valueAnimator: ValueAnimator = ValueAnimator()

    init {
        LayoutInflater.from(context).inflate(R.layout.view_defaultheaderview, this)

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
    }

    override fun isEnought(): Boolean {
        val currentHeight = layoutParams.height
        return currentHeight >= headerHeight / 2
    }

    override fun start() {
        changeHeight(layoutParams.height, headerHeight, {}, { startRotate() })
    }

    override fun end() {
        changeHeight(layoutParams.height, 0, { stopRotate() }, {})
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
            var params = layoutParams
            params.height = valueAnimator.animatedValue as Int
            layoutParams = params
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

    private fun startRotate() {
        initRotate()
        animatorSet.start()
    }

    private fun initRotate() {
        rotateAnimator1 = ObjectAnimator.ofFloat(iv1, "rotation", 0f, 360f).setDuration(1000)
        rotateAnimator1.repeatCount = INFINITE

        animatorSet = AnimatorSet()
        animatorSet.addListener(object :Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                iv1.rotation=0f
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        animatorSet.play(rotateAnimator1)
    }

    private fun stopRotate() {
        animatorSet.end()
        animatorSet.cancel()
    }

}