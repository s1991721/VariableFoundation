package com.jef.variablefoundation.views.refresh

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import com.jef.variablefoundation.utils.Logger

/**
 * Created by mr.lin on 2018/1/15.
 * 刷新控件
 */
class RefreshRecyclerView(context: Context, attrs: AttributeSet?, defStyle: Int) : LinearLayout(context, attrs, defStyle) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private var headerView: AttachView
    private var recyclerView = RecyclerView(context)
    private var footerView: AttachView

    var delay = 2000L

    companion object {
        const val STATE_NORMAL = 0
        const val STATE_DROP_DOWN = 1
        const val STATE_REFRESHING = 2
        const val STATE_DROP_UP = 3
        const val STATE_LOADING = 4
    }

    var currentState = STATE_NORMAL

    init {

        orientation = VERTICAL

        headerView = DefaultHeaderView(context)
        footerView = DefaultFooterView(context)

        addView(headerView)
        addView(recyclerView, 1)
        addView(footerView)

        recyclerView.overScrollMode = OVER_SCROLL_NEVER

        footerView.onAttachViewHeightChangeListener = object : AttachView.OnAttachViewHeightChangeListener {
            override fun onHeightChange(height: Int) {
                scrollTo(0, height)
            }
        }

    }

    //记录down位置
    var startY = 0f
    //横向滑动
    var lastX = 0f

    var onRefreshListener: OnRefreshListener? = null
    var onLoadMoreListener: OnLoadMoreListener? = null

    var loadEnable = true

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (isScrollToTop()) {

            Logger.i("RefreshRecyclerView", "ScrollToTop")
            when (ev.action) {

                MotionEvent.ACTION_DOWN -> {
                    startY = ev.rawY
                    lastX = ev.rawX
                }

                MotionEvent.ACTION_MOVE -> {
                    if (currentState != STATE_REFRESHING) {
                        val offset = ev.rawY - startY
                        if (offset > 0) {
                            changeState(STATE_DROP_DOWN)
                            headerView.setVisibleHeight(offset)
                            if (Math.abs(lastX - ev.rawX) < offset) {//非横向滑动
                                return true
                            }
                        } else {
                            changeState(STATE_NORMAL)
                        }
                    }
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    if (currentState == STATE_DROP_DOWN) {
                        var toState = STATE_NORMAL
                        if (headerView.isEnought()) {
                            toState = STATE_REFRESHING
                        }
                        changeState(toState)
                    }
                }

            }
        } else if (isScrollToBottom()) {

            Logger.i("RefreshRecyclerView", "ScrollToBottom")
            when (ev.action) {

                MotionEvent.ACTION_DOWN -> {
                    startY = ev.rawY
                    lastX = ev.rawX
                }

                MotionEvent.ACTION_MOVE -> {
                    if (currentState != STATE_LOADING && loadEnable) {
                        val offset = startY - ev.rawY
                        if (offset > 0) {
                            changeState(STATE_DROP_UP)
                            footerView.setVisibleHeight(offset)
                            if (Math.abs(lastX - ev.rawX) < offset) {//非横向滑动
                                return true
                            }
                        } else {
                            changeState(STATE_NORMAL)
                        }
                    }
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    if (currentState == STATE_DROP_UP) {
                        var toState = STATE_NORMAL
                        if (footerView.isEnought()) {
                            toState = STATE_LOADING
                        }
                        changeState(toState)
                    }
                }

            }
        }

        return super.dispatchTouchEvent(ev)
    }

    private fun changeState(state: Int) {
        when (currentState) {
            STATE_NORMAL -> {
                if (state == STATE_REFRESHING) {
                    notifyRefresh()
                }
                if (state == STATE_LOADING) {
                    notifyLoadMore()
                }
            }
            STATE_DROP_DOWN -> {
                if (state == STATE_REFRESHING) {
                    notifyRefresh()
                }
                if (state == STATE_NORMAL) {
                    notifyRefreshCancel()
                }
            }
            STATE_REFRESHING -> {
                if (state == STATE_NORMAL) {
                    notifyRefreshEnd()
                }
            }
            STATE_DROP_UP -> {
                if (state == STATE_LOADING) {
                    notifyLoadMore()
                }
                if (state == STATE_NORMAL) {
                    notifyLoadMoreCancel()
                }
            }
            STATE_LOADING -> {
                if (state == STATE_NORMAL) {
                    notifyLoadMoreEnd()
                }
            }

        }
        currentState = state
    }

    //刷新操作
    private fun isScrollToTop(): Boolean {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val position = layoutManager.findFirstVisibleItemPosition()
        if (position != 0) {
            return false
        }
        val firstVisiableChildView = layoutManager.findViewByPosition(position)
        return firstVisiableChildView!!.y.toInt() == 0
    }

    private fun notifyRefresh() {
        postDelayed({ onRefreshListener?.onRefresh() }, delay)
        headerView.start()
    }

    private fun notifyRefreshEnd() {
        headerView.end()
    }

    private fun notifyRefreshCancel() {
        headerView.cancel()
    }

    //更多操作
    private fun isScrollToBottom(): Boolean {
        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()
    }

    private fun notifyLoadMore() {
        postDelayed({ onLoadMoreListener?.onLoadMore() }, delay)
        footerView.start()
    }

    private fun notifyLoadMoreEnd() {
        footerView.end()
    }

    private fun notifyLoadMoreCancel() {
        footerView.cancel()
    }

    //对外使用
    fun setLayoutManager(layout: RecyclerView.LayoutManager) {
        recyclerView.layoutManager = layout
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        recyclerView.adapter = adapter
    }

    fun setHeaderView(view: AttachView) {
        headerView = view
        invalidate()
    }

    fun setFooterView(view: AttachView) {
        footerView = view
        invalidate()
    }

    fun startRefresh() {
        changeState(STATE_REFRESHING)
    }

    fun stopRefresh() {
        changeState(STATE_NORMAL)
    }

    fun stopLoadMore() {
        changeState(STATE_NORMAL)
    }

    interface OnRefreshListener {
        fun onRefresh()
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        onRefreshListener = null
        onLoadMoreListener = null
    }
}