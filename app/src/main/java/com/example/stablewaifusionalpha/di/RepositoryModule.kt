package com.example.stablewaifusionalpha.di

import android.content.Context
import com.example.stablewaifusionalpha.data.remote.ApiService
import com.example.stablewaifusionalpha.data.repository.PromptTemplatesRepositoryImpl
import com.example.stablewaifusionalpha.data.repository.QualitiesRepositoryImpl
import com.example.stablewaifusionalpha.data.repository.Text2ImageRepositoryImpl
import com.example.stablewaifusionalpha.data.repository.UpscalersRepositoryImpl
import com.example.stablewaifusionalpha.domain.repository.PromptTemplatesRepository
import com.example.stablewaifusionalpha.domain.repository.QualitiesRepository
import com.example.stablewaifusionalpha.domain.repository.Text2ImageRepository
import com.example.stablewaifusionalpha.domain.repository.UpscalersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun getUpscalersRepositoryImpl(apiService: ApiService): UpscalersRepository {
        return UpscalersRepositoryImpl(apiService)
    }

    @Singleton
    @Provides
    fun getText2ImageRepositoryImpl(apiService: ApiService): Text2ImageRepository {
        return Text2ImageRepositoryImpl(apiService)
    }

    @Singleton
    @Provides
    fun getQualitiesRepositoryImpl(@ApplicationContext context: Context): QualitiesRepository{
        return QualitiesRepositoryImpl(context)
    }

    @Singleton
    @Provides
    fun getPromptTemplatesRepositoryImpl(@ApplicationContext context: Context): PromptTemplatesRepository{
        return PromptTemplatesRepositoryImpl(context)
    }

}