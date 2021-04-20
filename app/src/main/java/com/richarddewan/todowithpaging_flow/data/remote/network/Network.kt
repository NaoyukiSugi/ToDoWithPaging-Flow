package com.richarddewan.todowithpaging_flow.data.remote.network

import com.richarddewan.todowithpaging_flow.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object Network {
    const val HEADER_ACCEPT = "Accept: */*"
    private const val NETWORK_TIME_OUT = 60L // time in sec
    private lateinit var okHttpClient: OkHttpClient

    fun create(baseUrl: String, cacheDir: File, cacheSize: Long): NetworkService {
        okHttpClient = OkHttpClient.Builder()
            .cache(Cache(cacheDir, cacheSize))
            .readTimeout(NETWORK_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkService::class.java)
    }
}
