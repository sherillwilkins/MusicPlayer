package com.w83ll43.musicplayer

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.hjq.toast.ToastUtils
import com.lzx.starrysky.StarrySky
import com.xuexiang.xui.XUI

class MusicPlayerApplication: Application() {

    //   它全局只会存在一份实例 并且在整个应用程序的生命周期内都不会回收
    //   因此是不存在内存泄漏风险的 用如下注解
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        XUI.init(this)
        XUI.debug(true)
        StarrySky.init(this).apply()
        ToastUtils.init(this)
    }

}