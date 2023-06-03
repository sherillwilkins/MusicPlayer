package com.w83ll43.musicplayer.ui.common

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xuexiang.xui.utils.StatusBarUtils

abstract class BaseActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
        initBinding()
        initData()
        initView()
        initBroadcastReceiver()
        initListener()
        initObserver()
        initBar()
    }

    protected open fun initBinding() {}
    protected open fun initView() {}
    protected open fun initData() {}
    protected open fun initListener() {}
    protected open fun initObserver() {}
    protected open fun initBroadcastReceiver() {}
    protected open fun initBar() {
        StatusBarUtils.translucent(this, Color.TRANSPARENT)
        StatusBarUtils.setStatusBarLightMode(this);
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}