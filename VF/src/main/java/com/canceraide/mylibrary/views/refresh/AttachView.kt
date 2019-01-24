package com.canceraide.mylibrary.views.refresh

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.canceraide.mylibrary.base.BaseActivity

/**
 * Created by mr.lin on 2018/1/16.
 * RefreshRecyclerView 的附加view
 */
abstract class AttachView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : FrameLayout(context, attrs, defStyleAttr) {

    var viewHeight = BaseActivity.dpToPx(50)
    var onAttachViewHeightChangeListener: OnAttachViewHeightChangeListener? = null

    abstract fun setVisibleHeight(height: Float)

    abstract fun isEnought(): Boolean

    abstract fun start()

    abstract fun end()

    abstract fun cancel()

    interface OnAttachViewHeightChangeListener {
        fun onHeightChange(height: Int)
    }

}