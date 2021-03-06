package com.jef.variablefoundation.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.jef.variablefoundation.R
import com.jef.variablefoundation.image.ImageLoader
import kotlinx.android.synthetic.main.view_actionbar.view.*
import java.lang.reflect.Method

/**
 * Created by mr.lin on 2018/11/14
 * 通用标题栏
 */
class ActionBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : LinearLayout(context, attrs, defStyleAttr) {

    private var function = -1

    companion object {
        const val FUNCTION_NONE = 0
        const val FUNCTION_LEFT_TITLE = 2
        const val FUNCTION_LEFT_BTN = 4
        const val FUNCTION_TITLE = 8
        const val FUNCTION_RIGHT_TITLE = 16
        const val FUNCTION_RIGHT_BTN = 32
        const val FUNCTION_INFO_LL = 64
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_actionbar, this)
        orientation = VERTICAL

        if (background == null) {
            setBackgroundResource(R.mipmap.nav_bg)
        }

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
        val statusBarEnable = typeArray.getBoolean(R.styleable.ActionBar_status_bar_enable, true)
        val statusBarBg = typeArray.getDrawable(R.styleable.ActionBar_status_bar_bg)

        typeArray.recycle()

        setFunction(function)
        setLeftText(leftText)
        setLeftDrawable(leftDrawable)
        setTitle(title)
        setRightText(rightText)
        setRightDrawable(rightDrawable)
        setStatusBar(statusBarEnable, statusBarBg)


        if (TextUtils.isEmpty(leftClick)) leftClick = "onBackClick"
        setLeftOnClickListener(getClickListener(leftClick))
        setRightOnClickListener(getClickListener(rightClick))
        setTitleOnClickListener(getClickListener(titleClick))

    }

    //设置功能
    fun setFunction(function: Int) {
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
        if (isFunctionEnable(FUNCTION_INFO_LL)) {
            infoLl.visibility = View.VISIBLE
        } else {
            infoLl.visibility = View.GONE
        }
    }

    //功能可用
    private fun isFunctionEnable(function: Int): Boolean {
        return (this.function and function) == function
    }

    fun setLeftText(text: String?) {
        if (!TextUtils.isEmpty(text)) {
            leftTv.text = text
        }
    }

    fun setLeftDrawable(drawable: Drawable?) {
        if (drawable != null) {
            leftIb.setImageDrawable(drawable)
        }
    }

    fun setTitle(text: String?) {
        if (!TextUtils.isEmpty(text)) {
            titleTv.text = text
        }
    }

    fun setRightText(text: String?) {
        if (!TextUtils.isEmpty(text)) {
            rightTv.text = text
        }
    }

    fun setRightTextDrawable(drawable: Drawable?) {
        rightTv.setCompoundDrawables(drawable, null, null, null)
    }

    fun setRightDrawable(drawable: Drawable?) {
        if (drawable != null) {
            rightIb.setImageDrawable(drawable)
        }
    }

    fun setStatusBar(enable: Boolean, drawable: Drawable?) {
        if (enable) {
            statusBar.visibility = View.VISIBLE
        } else {
            statusBar.visibility = View.GONE
            return
        }
        if (drawable != null) {
            statusBar.background = drawable
        }
    }

    fun setAvatar(url: String?) {
        ImageLoader.load(avatarIv, url).setCircle(true).setResId(R.mipmap.img_first_head).apply()
    }

    fun setAvatar(id: Int) {
        ImageLoader.load(avatarIv, id).setCircle(true).apply()
    }

    fun setName(name: String) {
        nameTv.text = name
    }

    fun setName(id: Int) {
        setName(context.getString(id))
    }

    fun setLeftOnClickListener(clickListener: OnClickListener?) {
        leftTv.setOnClickListener(clickListener)
        leftIb.setOnClickListener(clickListener)
    }

    fun setRightOnClickListener(clickListener: OnClickListener?) {
        rightTv.setOnClickListener(clickListener)
        rightIb.setOnClickListener(clickListener)
    }

    fun setTitleOnClickListener(clickListener: OnClickListener?) {
        titleTv.setOnClickListener(clickListener)
    }

    fun setAvatarOnClickListener(clickListener: OnClickListener?) {
        avatarIv.setOnClickListener(clickListener)
    }

    fun setNameOnClickListener(clickListener: OnClickListener?) {
        nameTv.setOnClickListener(clickListener)
    }

    fun getClickListener(method: String?): OnClickListener? {
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