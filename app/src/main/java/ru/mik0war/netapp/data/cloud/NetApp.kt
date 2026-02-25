package ru.mik0war.netapp.data.cloud

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetApp {

    private const val BASE_URL = "http://192.168.15.221:5000"

    // Настройка логирования
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        // Уровень детализации: BODY выводит всё, включая тела запросов/ответов
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val api : NetworkAPI by lazy {
        retrofit.create(NetworkAPI::class.java)
    }
}