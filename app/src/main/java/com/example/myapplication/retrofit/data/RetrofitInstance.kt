package com.example.myapplication.retrofit.data


import com.example.myapplication.retrofit.data.service.PostService
import com.example.sample.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private const val BASE_URL = "http://192.168.0.5:8080/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient(provideLoggingInterceptor()))
            .addConverterFactory(
                GsonConverterFactory.create()

                // Data Class 의 Code Convention 을 정할 수 있다
//                        GsonConverterFactory.create(
//                        GsonBuilder()
//                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//                            .create())
            )
            .build()
    }

    private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                connectTimeout(30000, TimeUnit.MILLISECONDS)
                writeTimeout(30000, TimeUnit.MILLISECONDS)
                readTimeout(30000, TimeUnit.MILLISECONDS)

                if (BuildConfig.DEBUG) {
                    // 디버그 모드에서만 로그 찍히게 ...
                    addInterceptor(interceptor)
                }

                // 만약 헤더에 토큰을 넣어야 한다면...
                addInterceptor {
                    it.proceed(
                        it.request()
                            .newBuilder()
//                    .addHeader(API_HEADER, "Bearer ${sp.getAccessToken()}")
                            .build()
                    )
                }
            }.build()

    // 로그 표시
    private fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    val postService: PostService by lazy {
        retrofit.create(PostService::class.java)
    }

}