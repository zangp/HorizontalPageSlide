package com.perry.horizontal.slide.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.viewpager.widget.ViewPager
import com.perry.horizontal.slide.R
import com.perry.horizontal.slide.utils.dp2px

/**
 * @CreateDate: 2022/7/9
 * @CreateTime: 11:03
 *
 * @Author : zang.peng
 * @Email : zangp_hq@163.com
 * @Version : 1.0
 */
class PageSlideTabView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : HorizontalScrollView(context, attrs, defStyleAttr) {

    companion object {
        const val DEFAULT_INDICATOR_HEIGHT = 7
    }

    private var tabsContainer: LinearLayoutCompat

    /**
     * 下划线（指示器）高度
     */
    private var indicatorHeight: Int = DEFAULT_INDICATOR_HEIGHT.dp2px(context)
    private var indicatorPaint: Paint

    private var tabIsExpand = true
//    private var tabLeftPadding = 0
//    private var tabRightPadding = 0
    /**
     * tab之间的间距
     */
    private var tabSpace = 0

    /**
     * 下划线（指示器）颜色
     */
    private var indicatorColor: Int = 0xFF999999.toInt()
    private var viewPager: ViewPager? = null // 嵌套在内部的ViewPager
    private var tabTextColor: Int = 0xFF999999.toInt()
    private var tabTextSize: Float = 18f

    /**
     * tab的个数
     */
    private var tabNum: Int = 0

    /**
     * 当前选中的tab
     */
    private var currentItemIndex = 0
    private var indicatorOffsetPixels = 0

    /**
     * 指示器当前偏移比例
     */
    private var indicatorOffset = 0.0f

    inner class ViewPagerScrollChangeListener : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            Log.d("zp_test", "onPageScrolled positionOffset = $positionOffset position: $position")
            // 这个position与选中的position有细微的差别，这个是代表当前偏移量所基于的页面。
            currentItemIndex = position
            // positionOffset：滑动偏移的百分比，往右边tab滑动时，从0--》1，往左边滑动时，从1--》0
            // positionOffsetPixels：滑动偏移的像素值，不区分方向
            if (indicatorOffsetPixels == positionOffsetPixels) return
            indicatorOffsetPixels = positionOffsetPixels
            indicatorOffset = positionOffset
            invalidate()
        }

        override fun onPageSelected(position: Int) {
            Log.d("zp_test", "onPageSelected position = $position")

        }

        override fun onPageScrollStateChanged(state: Int) {
            Log.w("zp_test", "onPageScrollStateChanged state = $state")
        }

    }

    init {
        isFillViewport = true // 是否充满整个视图

        tabsContainer = LinearLayoutCompat(this.context)
        tabsContainer.orientation = LinearLayoutCompat.HORIZONTAL
        tabsContainer.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        addView(tabsContainer)
        getAttrsValue(context, attrs)

        // 初始化底部下划线画笔
        indicatorPaint = Paint()
        indicatorPaint.isAntiAlias = true
        indicatorPaint.color = indicatorColor
        indicatorPaint.style = Paint.Style.FILL
    }

    private fun getAttrsValue(context: Context, attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.PageSlideTabView)
        indicatorHeight = a.getDimensionPixelSize(
            R.styleable.PageSlideTabView_indicatorHeight,
            indicatorHeight
        )
        indicatorColor = a.getColor(
            R.styleable.PageSlideTabView_indicatorColor,
            indicatorColor
        )
        tabIsExpand = a.getBoolean(
            R.styleable.PageSlideTabView_tabExpand,
            true
        )
//        tabLeftPadding = a.getDimensionPixelSize(
//            R.styleable.PageSlideTabView_tabLeftPadding,
//            tabLeftPadding
//        )
//        tabRightPadding = a.getDimensionPixelSize(
//            R.styleable.PageSlideTabView_tabRightPadding,
//            tabRightPadding
//        )
        tabSpace = a.getDimensionPixelSize(
            R.styleable.PageSlideTabView_tabSpace,
            tabSpace
        )
        tabTextColor = a.getColor(
            R.styleable.TabLayout_tabTextColor,
            tabTextColor
        )
        tabTextSize = a.getDimension(
            R.styleable.PageSlideTabView_tabTextSize,
            tabTextSize
        )
        a.recycle()
    }

    fun attachToViewPager(pager: ViewPager?) {
        if (pager == null) return

        viewPager = pager
        val adapter = pager.adapter
            ?: throw IllegalAccessException("you did not set ViewPager2's adapter")

        tabsContainer.removeAllViews()

        pager.addOnPageChangeListener(ViewPagerScrollChangeListener())

        tabNum = adapter.count
        for (i in 0 until tabNum) {
            addSingleTextTab(adapter.getPageTitle(i)?.toString(), i)?.apply {
                tabsContainer.addView(this)
            }
        }
    }

//    private fun attachToViewPager2(pager: ViewPager2?) {
//        if (pager == null) return
//        val adapter = pager.adapter
//            ?: throw IllegalAccessException("you did not set ViewPager2's adapter")
//
//        tabsContainer.removeAllViews()
//
//        val tabNum = adapter.itemCount
//        for (i in 0 until tabNum) {
//            tabsContainer.addView(addSingleTextTab("", i))
//        }
//    }

    private fun addSingleTextTab(tabText: String?, index: Int): TextView? {
        if (tabText == null) return null

        val textTab = AppCompatTextView(context)
        textTab.text = tabText
        textTab.textSize = tabTextSize
        textTab.setTextColor(tabTextColor)
        textTab.gravity = Gravity.CENTER
        // 第一个tab不设置padding left
        when (index) {
            0 -> {
                textTab.setPadding(0, 0, tabSpace / 2, 0)
            }
            tabNum - 1 -> {
                textTab.setPadding(tabSpace / 2, 0, 0, 0)
            }
            else -> {
                textTab.setPadding(tabSpace / 2, 0, tabSpace / 2, 0)
            }
        }

        var params = LinearLayoutCompat.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT)
        if (!tabIsExpand) {
            params = LinearLayoutCompat.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        } else {
            params.weight = 1f
        }
        textTab.layoutParams = params
        return textTab
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        indicatorPaint.color = indicatorColor

        val childCount = tabsContainer.childCount
        if (currentItemIndex in 0 until childCount) {
            val tabView = tabsContainer.getChildAt(currentItemIndex) ?: return
            // 两个tab之间可移动的总距离
            var totalMoveDis = 0
            if (currentItemIndex + 1 < childCount) {
                totalMoveDis =
                    tabView.width / 2 + tabsContainer.getChildAt(currentItemIndex + 1).width / 2
            }

            canvas?.drawRoundRect(
                tabView.left.toFloat() + totalMoveDis * indicatorOffset,
                (tabView.bottom - indicatorHeight).toFloat(),
                tabView.right.toFloat() + totalMoveDis * indicatorOffset,
                tabView.bottom.toFloat(),
                indicatorHeight / 2f,
                indicatorHeight / 2f,
                indicatorPaint
            )
        }
    }
}