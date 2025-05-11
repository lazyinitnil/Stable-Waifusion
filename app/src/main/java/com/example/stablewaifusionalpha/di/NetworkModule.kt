package com.example.stablewaifusionalpha.di

import com.example.stablewaifusionalpha.core.constants.BASE_URL
import com.example.stablewaifusionalpha.data.remote.ApiService
import com.example.stablewaifusionalpha.data.remote.websocket.WebSocketClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @Named("base_url")
    fun provideBaseUrl(): String = BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
      /*  .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(0, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)*/
        .build()

    @Singleton
    @Provides
    fun provideRetrofitClient(
        @Named("base_url") baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideApiClient(
        retrofit: Retrofit
    ): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideWebSocketClient(
        okHttpClient: OkHttpClient,
        @Named("base_url") baseUrl: String
    ): WebSocketClient = WebSocketClient(okHttpClient, baseUrl)

}