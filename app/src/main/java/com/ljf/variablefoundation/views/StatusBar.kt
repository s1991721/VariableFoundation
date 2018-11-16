package com.ljf.variablefoundation.views

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * Created by mr.lin on 2018/11/16
 * 沉浸式
 */
class StatusBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : FrameLayout(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    //系统状态栏高
    @SuppressLint("PrivateApi")
    fun getStatusBarHeight(context: Context): Int {
        return if (isNeedShowStatusBar()) {
            try {
                val cls = Class.forName("com.android.internal.R\$dimen")
                val obj = cls.newInstance()
                val field = cls.getField("status_bar_height")
                val heightId = field.get(obj).toString().toInt()
                val height = context.resources.getDimensionPixelSize(heightId)
                height
            } catch (e: Exception) {
                return (context.resources.displayMetrics.density * 25 + 0.5).toInt()
            }
        } else {
            0
        }

    }

    private fun isNeedShowStatusBar(): Boolean {
        return isInEditMode || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
    }

    override fun getSuggestedMinimumHeight(): Int {
        return getStatusBarHeight(context)
    }

}