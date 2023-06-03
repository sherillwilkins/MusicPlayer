package com.w83ll43.musicplayer.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.w83ll43.musicplayer.MusicPlayerApplication
import com.w83ll43.musicplayer.logic.model.LocalUser
import com.w83ll43.musicplayer.logic.model.UserInfo

object UserDao {

    private fun sharePreferences() =
        MusicPlayerApplication.context.getSharedPreferences("user", Context.MODE_PRIVATE)

    /**
     * 存储用户信息
     * @param userInfo
     */
    fun saveUserInfo(userInfo: UserInfo) {
        sharePreferences().edit {
            putString("userInfo", Gson().toJson(userInfo))
        }
    }

    /**
     * 获取用户信息
     */
    fun getUserInfo(): UserInfo {
        val userJson = sharePreferences().getString("userInfo", "")
        return Gson().fromJson(userJson, UserInfo::class.java)
    }

    /**
     * 判断是否登录
     */
    fun isLogin() = sharePreferences().contains("userInfo")

    /**
     * 退出登录
     */
    fun logout() {
        sharePreferences().edit {
            clear()
        }
    }

    fun getUser(): LocalUser {
        val userInfo = getUserInfo()
        val localUser = LocalUser()
        localUser.nickname = userInfo.userDetail.profile.nickname
        localUser.avatarUrl = userInfo.userDetail.profile.avatarUrl
        localUser.userId = userInfo.userDetail.userPoint.userId!!
        localUser.listenSongs = userInfo.userDetail.listenSongs
        localUser.birthday = userInfo.userDetail.profile.birthday
        return localUser
    }
}