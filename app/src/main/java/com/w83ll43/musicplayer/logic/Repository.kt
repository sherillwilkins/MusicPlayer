package com.w83ll43.musicplayer.logic

import androidx.lifecycle.liveData
import com.w83ll43.musicplayer.logic.dao.UserDao
import com.w83ll43.musicplayer.logic.model.UserInfo
import com.w83ll43.musicplayer.logic.network.MusicPlayerNetwork
import com.w83ll43.musicplayer.ui.login.LoginData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

object Repository {

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

    fun sent(phone: String) = fire(Dispatchers.IO) {
        val result = MusicPlayerNetwork.sent(phone)
        if (result.code == 200) {
            Result.success("发送验证码成功！")
        } else Result.failure(
            RuntimeException("sent 响应的响应码为 ${result.code} ")
        )
    }

    fun login(data: LoginData) = fire(Dispatchers.IO) {
        coroutineScope {
            val loginResponse = async {
                MusicPlayerNetwork.login(data)
            }.await()

            val loginStatusResponse = async {
                MusicPlayerNetwork.getLoginStatus()
            }.await()

            val userDetailResponse = async {
                MusicPlayerNetwork.getUserDetail()
            }.await()

            if (loginResponse.code == 200 && loginStatusResponse.data.code == 200 && userDetailResponse.code == 200) {
                val userInfo = UserInfo(loginStatusResponse.data.account, userDetailResponse)
                println(userInfo)
                UserDao.saveUserInfo(userInfo)
                Result.success(userInfo)
            } else Result.failure(
                RuntimeException(
                    "login 响应的响应码为 ${loginResponse.code} " +
                            "loginStatus 响应的响应码为 ${loginStatusResponse.data.code} " +
                            "userDetail 响应的响应码为 ${userDetailResponse.code}"
                )
            )
        }
    }

    // 获取用户歌单
    fun getPlayList() = fire(Dispatchers.IO) {
        val result = MusicPlayerNetwork.getPlayList()
        if (result.code == 200) Result.success(result.playlist)
        else Result.failure(RuntimeException("getPlayList 的响应码是 ${result.code}"))
    }

    // 搜索歌曲
    fun search(keywords: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val searchResponse = async {
                MusicPlayerNetwork.search(keywords)
            }.await()

            if (searchResponse.code == 200) {
                Result.success(searchResponse)
            } else Result.failure(
                RuntimeException("search 响应的响应码为 ${searchResponse.code}")
            )

        }
    }

    // 获取歌单所有歌曲
    fun getPlayListDetail(data: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val result = async {
                MusicPlayerNetwork.getPlayListTrack(data)
            }.await()

            if (result.code == 200) Result.success(result.songs)
            else Result.failure(RuntimeException("getPlayListDetail 的响应码是 ${result.code}"))
        }
    }

    // 获取歌词
    fun getLyric(songId: Int) = fire(Dispatchers.IO) {
        val result = MusicPlayerNetwork.getLyric(songId)
        if (result.code == 200) Result.success(result)
        else Result.failure(RuntimeException("getLyric 的响应码是 ${result.code}"))
    }

    // 获取音乐播放路径
    fun getMusicUrl(id: String) = fire(Dispatchers.IO) {
        val result = MusicPlayerNetwork.getMusicUrl(id)
        if (result.code == 200) Result.success(result)
        else Result.failure(RuntimeException("getMusicUrl 的响应码是 ${result.code}"))
    }

    // 获取评论
    fun getMusicComment(id: String) = fire(Dispatchers.IO) {
        val result = MusicPlayerNetwork.getMusicComment(id)
        if (result.code == 200) Result.success(result)
        else Result.failure(RuntimeException("getMusicComment 的响应码是 ${result.code}"))
    }

    // 榜单内容摘要
    fun getTopList() = fire(Dispatchers.IO) {
        val result = MusicPlayerNetwork.getTopList()
        if (result.code == 200) Result.success(result)
        else Result.failure(RuntimeException("getTopList 的响应码是 ${result.code}"))
    }

    /**
     * 获取推荐歌单
     */
    fun getRecommendPlayList() = fire(Dispatchers.IO) {
        val result = MusicPlayerNetwork.getRecommendPlayList()
        if (result.code == 200) Result.success(result)
        else Result.failure(RuntimeException("getRecommendPlayList 的响应码是 ${result.code}"))
    }

}