package com.w83ll43.musicplayer.logic.network

import com.w83ll43.musicplayer.logic.network.service.NeteaseCloudMusicService
import com.w83ll43.musicplayer.ui.login.LoginData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object MusicPlayerNetwork {

    private val neteaseCloudMusicService = ServiceCreator.create(NeteaseCloudMusicService::class.java)
    suspend fun sent(phone: String) = neteaseCloudMusicService.sent(phone).await()
    suspend fun login(data: LoginData) = neteaseCloudMusicService.login(data.phone, data.code).await()
    suspend fun search(keywords: String) = neteaseCloudMusicService.search(keywords).await()
    suspend fun getLoginStatus() = neteaseCloudMusicService.getLoginStatus().await()
    suspend fun getUserDetail() = neteaseCloudMusicService.getUserDetail().await()
    suspend fun getPlayList() = neteaseCloudMusicService.getPlayList().await()
    suspend fun getPlayListTrack(data: String) = neteaseCloudMusicService.getPlayListTrack(data, 100).await()
    suspend fun getLyric(songId: Int) = neteaseCloudMusicService.getLyric(songId).await()
    suspend fun getMusicUrl(songId: String) = neteaseCloudMusicService.getMusicUrl(songId).await()
    suspend fun getMusicComment(id: String) = neteaseCloudMusicService.getComment(id).await()
    suspend fun getTopList() = neteaseCloudMusicService.getTopList().await()
    suspend fun getRecommendPlayList() = neteaseCloudMusicService.getRecommendPlayList().await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("响应体为空"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    println("响应失败！$t")
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}