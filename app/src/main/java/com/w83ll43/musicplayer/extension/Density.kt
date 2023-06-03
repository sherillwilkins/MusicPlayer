package com.w83ll43.musicplayer.extension

import com.w83ll43.musicplayer.MusicPlayerApplication

/**
 * 根据手机的分辨率将dp转成为px。
 */
fun dp2px(dp: Float): Int {
    val scale = MusicPlayerApplication.context.resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率将px转成dp。
 */
fun px2dp(px: Float): Int {
    val scale = MusicPlayerApplication.context.resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}

/**
 * 获取屏幕高度
 */
val screenHeight = MusicPlayerApplication.context.resources.displayMetrics.heightPixels

/**
 * 获取屏幕宽度
 */
val screenWidth = MusicPlayerApplication.context.resources.displayMetrics.widthPixels
