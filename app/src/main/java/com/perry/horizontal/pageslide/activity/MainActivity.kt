package com.perry.horizontal.pageslide.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.perry.horizontal.pageslide.databinding.ActivityMainBinding
import com.perry.horizontal.pageslide.databinding.FragmentViewABinding
import com.perry.horizontal.slide.adapter.ViewPagerBaseAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var tabTitles: MutableList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        tabTitles = mutableListOf("推荐", "附近", "小姐姐")
        binding.viewPager.adapter = ViewPagerAdapter(mutableListOf("A", "B", "C"))
        binding.pageSlideTabView.attachToViewPager(binding.viewPager)
    }

    inner class ViewPagerAdapter(private val viewPagerData: MutableList<String>) :
        ViewPagerBaseAdapter<String>(viewPagerData) {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val fragmentView =
                FragmentViewABinding.inflate(
                    LayoutInflater.from(container.context),
                    container,
                    false
                )
            fragmentView.tvContentView.text = viewPagerData[position]
            container.addView(fragmentView.root)
            return fragmentView.root
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View?)
        }

        override fun getTitles(): MutableList<String>? {
            return tabTitles
        }

    }
}