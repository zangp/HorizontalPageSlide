package com.perry.horizontal.slide.adapter

import android.view.View
import androidx.viewpager.widget.PagerAdapter
import com.perry.horizontal.slide.impl.PageAdapterImpl

/**
 * @CreateDate: 2022/7/16
 * @CreateTime: 10:02
 *
 * @Author : zang.peng
 * @Email : zangp_hq@163.com
 * @Version : 1.0
 */
abstract class ViewPagerBaseAdapter<T>(private var data: MutableList<T>) : PagerAdapter(),
    PageAdapterImpl {
    override fun getCount(): Int {
        return if (data.isEmpty()) 0 else data.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val titles = getTitles()
        if (titles?.isNotEmpty() == true) {
            if (position >= 0 && position < titles.size) {
                return titles[position]
            }
        }
        return super.getPageTitle(position)
    }
}