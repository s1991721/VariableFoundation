package com.ljf.variablefoundation.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.ljf.variablefoundation.R
import com.ljf.variablefoundation.base.BaseFragment
import kotlinx.android.synthetic.main.view_tabitem.view.*

/**
 * Created by mr.lin on 2018/11/14
 * 方便管理Fragment的TabItem
 */
class TabItem<F : BaseFragment>(context: Context, attrs: AttributeSet?, defStyleAttr: Int, fragmentClass: Class<F>?) : LinearLayout(context, attrs, defStyleAttr) {

    private var tip: String? = null
    private var selectColor: Int
    private var unSelectColor: Int
    private var selectIcon: Drawable? = null
    private var unSelectIcon: Drawable? = null

    private var isSelect = false

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)
    constructor(context: Context, fragmentClass: Class<F>) : this(context, null, 0, fragmentClass)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_tabitem, this, true)

        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.TabItem)
        tip = typeArray?.getString(R.styleable.TabItem_tip)
        selectColor = typeArray.getColor(R.styleable.TabItem_selectColor, Color.BLACK)
        unSelectColor = typeArray.getColor(R.styleable.TabItem_unSelectColor, Color.parseColor("#838383"))
        selectIcon = typeArray?.getDrawable(R.styleable.TabItem_selectIcon)
        unSelectIcon = typeArray?.getDrawable(R.styleable.TabItem_unSelectIcon)
        typeArray?.recycle()

        orientation = VERTICAL
        gravity = Gravity.CENTER
        setPadding(2, 2, 2, 2)

        if (fragmentClass != null) {
            tag = fragmentClass.name
        }

        show()
    }

    fun setTip(str: String) {
        tip = str
        show()
    }

    fun setColor(select: Int, unselect: Int) {
        selectColor = select
        unSelectColor = unselect
        show()
    }

    fun setIcon(select: Drawable, unselect: Drawable) {
        selectIcon = select
        unSelectIcon = unselect
        show()
    }

    fun setSelect(select: Boolean) {
        isSelect = select
        show()
    }

    private fun show() {
        name.text = tip
        if (isSelect) {
            name.setTextColor(selectColor)
            image.setImageDrawable(selectIcon)
        } else {
            name.setTextColor(unSelectColor)
            image.setImageDrawable(unSelectIcon)
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener { v ->
            if (!isSelect) {
                l?.onClick(v)
                isSelect = !isSelect
                show()
            }
        }
    }
}