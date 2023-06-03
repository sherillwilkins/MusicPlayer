package com.w83ll43.musicplayer.extension

import android.content.Context
import android.widget.Toast
import com.w83ll43.musicplayer.MusicPlayerApplication
import java.security.MessageDigest

fun String.md5Encode(): String {
    val hash = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    val hex = StringBuilder(hash.size * 2)
    for (b in hash) {
        var str = Integer.toHexString(b.toInt())
        if (b < 0x10) {
            str = "0$str"
        }
        hex.append(str.substring(str.length - 2))
    }
    return hex.toString()
}

fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(MusicPlayerApplication.context, this, duration).show()
}

/**
 * [获取应用程序版本名称信息]
 *
 * @param context
 * @return 当前应用的版本名称
 */
@Synchronized
fun getPackageName(context: Context): String? {
    try {
        val packageManager = context.packageManager
        val packageInfo = packageManager.getPackageInfo(
            context.packageName, 0
        )
        return packageInfo.packageName
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}