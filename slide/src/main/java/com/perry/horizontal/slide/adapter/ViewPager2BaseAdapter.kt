package com.perry.horizontal.slide.adapter

import androidx.recyclerview.widget.RecyclerView

/**
 * @CreateDate: 2022/7/15
 * @CreateTime: 07:52
 *
 * @Author : zang.peng
 * @Email : zangp_hq@163.com
 * @Version : 1.0
 */
abstract class ViewPager2BaseAdapter<T, VH : RecyclerView.ViewHolder>(private val data: MutableList<T>) :
    RecyclerView.Adapter<VH>() {

}