package com.example.stablewaifusionalpha.di

import com.example.stablewaifusionalpha.core.BASE_URL
import com.example.stablewaifusionalpha.data.remote.ApiService
import com.example.stablewaifusionalpha.data.repository.Text2ImageRepositoryImpl
import com.example.stablewaifusionalpha.data.repository.UpscalersRepositoryImpl
import com.example.stablewaifusionalpha.domain.repository.Text2ImageRepository
import com.example.stablewaifusionalpha.domain.repository.UpscalersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @Named("base_url")
    fun getBaseUrl(): String = BASE_URL

    @Singleton
    @Provides
    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(90, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun getRetrofitClient(
        @Named("base_url") baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun getApiClient(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}