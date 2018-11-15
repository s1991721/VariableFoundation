package com.ljf.variablefoundation.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.ljf.variablefoundation.R
import kotlinx.android.synthetic.main.view_actionbar.view.*
import java.lang.reflect.Method

/**
 * Created by mr.lin on 2018/11/14
 * 通用标题栏
 */
class ActionBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : RelativeLayout(context, attrs, defStyleAttr) {

    private var function = -1

    companion object {
        val FUNCTION_NONE = 0
        val FUNCTION_LEFT_TITLE = 2
        val FUNCTION_LEFT_BTN = 4
        val FUNCTION_TITLE = 8
        val FUNCTION_RIGHT_TITLE = 16
        val FUNCTION_RIGHT_BTN = 32
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_actionbar, this)

        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.ActionBar)

        val leftText = typeArray.getString(R.styleable.ActionBar_left_text)
        val leftDrawable = typeArray.getDrawable(R.styleable.ActionBar_left_icon)
        val title = typeArray.getString(R.styleable.ActionBar_title_text)
        val rightText = typeArray.getString(R.styleable.ActionBar_right_text)
        val rightDrawable = typeArray.getDrawable(R.styleable.ActionBar_right_icon)
        val function = typeArray.getInt(R.styleable.ActionBar_function, (FUNCTION_TITLE or FUNCTION_LEFT_BTN))
        var leftClick = typeArray.getString(R.styleable.ActionBar_leftOnClick)
        val rightClick = typeArray.getString(R.styleable.ActionBar_rightOnClick)
        val titleClick = typeArray.getString(R.styleable.ActionBar_titleOnClick)

        typeArray.recycle()

        setFunction(function)
        setLeftText(leftText)
        setLeftDrawable(leftDrawable)
        setTitle(title)
        setRightText(rightText)
        setRightDrawable(rightDrawable)

        if (TextUtils.isEmpty(leftClick)) leftClick = "onBackClick"
        setLeftOnClickListener(getClickListener(leftClick))
        setRightOnClickListener(getClickListener(rightClick))
        setTitleOnClickListener(getClickListener(titleClick))

    }

    //设置功能
    private fun setFunction(function: Int) {
        if (this.function == function) {
            return
        }
        this.function = function

        if (isFunctionEnable(FUNCTION_LEFT_TITLE)) {
            leftTv.visibility = View.VISIBLE
        } else {
            leftTv.visibility = View.GONE
        }
        if (isFunctionEnable(FUNCTION_LEFT_BTN)) {
            leftIb.visibility = View.VISIBLE
        } else {
            leftIb.visibility = View.GONE
        }
        if (isFunctionEnable(FUNCTION_TITLE)) {
            titleTv.visibility = View.VISIBLE
        } else {
            titleTv.visibility = View.GONE
        }
        if (isFunctionEnable(FUNCTION_RIGHT_TITLE)) {
            rightTv.visibility = View.VISIBLE
        } else {
            rightTv.visibility = View.GONE
        }
        if (isFunctionEnable(FUNCTION_RIGHT_BTN)) {
            rightIb.visibility = View.VISIBLE
        } else {
            rightIb.visibility = View.GONE
        }

    }

    //功能可用
    private fun isFunctionEnable(function: Int): Boolean {
        return (this.function and function) == function
    }

    private fun setLeftText(text: String?) {
        if (!TextUtils.isEmpty(text)) {
            leftTv.text = text
        }
    }

    private fun setLeftDrawable(drawable: Drawable?) {
        if (drawable != null) {
            leftIb.setImageDrawable(drawable)
        }
    }

    private fun setTitle(text: String?) {
        if (!TextUtils.isEmpty(text)) {
            titleTv.text = text
        }
    }

    private fun setRightText(text: String?) {
        if (!TextUtils.isEmpty(text)) {
            rightTv.text = text
        }
    }

    private fun setRightDrawable(drawable: Drawable?) {
        if (drawable != null) {
            rightIb.setImageDrawable(drawable)
        }
    }

    private fun setLeftOnClickListener(clickListener: OnClickListener?) {
        leftTv.setOnClickListener(clickListener)
        leftIb.setOnClickListener(clickListener)
    }

    private fun setRightOnClickListener(clickListener: OnClickListener?) {
        rightTv.setOnClickListener(clickListener)
        rightIb.setOnClickListener(clickListener)
    }

    private fun setTitleOnClickListener(clickListener: OnClickListener?) {
        titleTv.setOnClickListener(clickListener)
    }

    private fun getClickListener(method: String?): OnClickListener? {
        if (!TextUtils.isEmpty(method)) {
            return try {
                val m = context.javaClass.getMethod(method!!, View::class.java)
                InjectOnClickListener(m, context)
            } catch (e: Exception) {
                null
            }
        }
        return null
    }

    //反射触发
    class InjectOnClickListener(private val method: Method, private val target: Any) : OnClickListener {

        override fun onClick(v: View?) {
            method.invoke(target, v)
        }

    }
}