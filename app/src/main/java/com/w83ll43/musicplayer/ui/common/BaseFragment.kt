package com.w83ll43.musicplayer.ui.common

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    open fun initBinding() {}

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
        initListener()
        initObserver()
        initBroadcastReceiver()
    }

    open fun initBroadcastReceiver(){}

    open fun initView() {}

    open fun initData() {}

    open fun initListener() {}

    open fun initObserver() {}

}