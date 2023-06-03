package com.w83ll43.musicplayer.ui.home.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.w83ll43.musicplayer.databinding.FragmentMoreBinding
import com.w83ll43.musicplayer.ui.common.BaseFragment
import com.w83ll43.musicplayer.ui.common.adapter.ViewPage2Adapter
import com.w83ll43.musicplayer.ui.home.more.tab_layout.MoreFragmentOne
import com.w83ll43.musicplayer.ui.home.more.tab_layout.MoreFragmentTwo

class MoreFragment : BaseFragment() {
    private val tabsTitle = arrayOf("推荐歌单", "排行榜")
    private lateinit var viewPager :ViewPager2
    private lateinit var _binding: FragmentMoreBinding
    private var fragments :MutableList<Fragment> = ArrayList()
    val binding: FragmentMoreBinding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //添加fragments
        fragments.apply {
            add(MoreFragmentOne())
            add(MoreFragmentTwo())
//            add(MoreFragmentThree())
        }
        viewPager = binding.moreFragmentViewPage
        viewPager.adapter = ViewPage2Adapter(fragments, this)
        val tabLayout = binding.moreFragmentTab
        TabLayoutMediator(tabLayout, binding.moreFragmentViewPage) { tab, position ->
            tab.text = tabsTitle[position]
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}