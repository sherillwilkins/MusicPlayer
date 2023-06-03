package com.w83ll43.musicplayer.logic.network

import com.w83ll43.musicplayer.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    private var okHttpClient: OkHttpClient

    init {
        val client = OkHttpClient.Builder()
        // 在 Debug 模式下设置日志拦截器
        if (BuildConfig.DEBUG) {
            val logger =
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            client.addInterceptor(logger)
        }
        okHttpClient = client.build()
    }

    private const val BASE_URL = "http://47.113.223.234:3000"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

}