package com.w83ll43.musicplayer.logic.network.service

import com.w83ll43.musicplayer.logic.model.*
import com.w83ll43.musicplayer.logic.model.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NeteaseCloudMusicService {

    /**
     * 发送验证码
     */
    @GET("/captcha/sent")
    fun sent(
        @Query("phone") phone: String
    ): Call<ResponseCommon>

    /**
     * 验证码登录
     */
    @GET("/captcha/verify")
    fun login(
        @Query("phone") phone: String,
        @Query("captcha") code: String
    ): Call<ResponseCommon>

    /**
     * 获取用户登录信息
     */
    @GET("/login/status")
    fun getLoginStatus(): Call<LoginStatus>

    /**
     * 获取用户详情
     */
    @GET("/user/detail")
    fun getUserDetail(@Query("uid") uid: Long = 572352988): Call<UserDetail>

    /**
     * 搜索
     */
    @GET("/search")
    fun search(@Query("keywords") keywords: String): Call<SearchResponse>

    /**
     * 获取用户歌单
     */
    @GET("/user/playlist")
    fun getPlayList(@Query("uid") uid: Long = 572352988): Call<UserPlayList>

    /**
     * 获取歌单所有歌曲
     */
    @GET("/playlist/track/all")
    fun getPlayListTrack(@Query("id") id: String, @Query("limit") limit: Int): Call<SongList>

    /**
     * 获取歌词
     * 可以不用登录
     */
    @GET("/lyric")
    fun getLyric(@Query("id") id: Int): Call<Lyric>

    /**
     * 获取音乐 URL
     */
    @GET("/song/url")
    fun getMusicUrl(@Query("id") id: String): Call<MusicUrl>

    /**
     * 获取音乐评论
     */
    @GET("/comment/music")
    fun getComment(@Query("id") id: String): Call<MusicComment>

    /**
     * 榜单内容摘要
     */
    @GET("/toplist/detail")
    fun getTopList(): Call<HotTopList>

    /**
     * 获取推荐歌单
     */
    @GET("/personalized")
    fun getRecommendPlayList(): Call<RecommendPlayList>
}