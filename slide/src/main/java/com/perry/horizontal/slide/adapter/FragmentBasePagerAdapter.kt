package com.perry.horizontal.slide.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.perry.horizontal.slide.impl.PageAdapterImpl

/**
 * @CreateDate: 2022/7/16
 * @CreateTime: 15:43
 *
 * @Author : zang.peng
 * @Email : zangp_hq@163.com
 * @Version : 1.0
 */
abstract class FragmentBasePagerAdapter(manager: FragmentManager, private var fragments: MutableList<Fragment>) :
    FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT), PageAdapterImpl {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
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