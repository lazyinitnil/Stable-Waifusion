package com.example.stablewaifusionalpha.di

import android.content.Context
import com.example.stablewaifusionalpha.data.remote.ApiService
import com.example.stablewaifusionalpha.core.common.ClientIdProvider
import com.example.stablewaifusionalpha.data.local.preference.AppPreferences
import com.example.stablewaifusionalpha.data.remote.websocket.WebSocketClient
import com.example.stablewaifusionalpha.data.repository.GenerateImageRepositoryImpl
import com.example.stablewaifusionalpha.data.repository.OnboardingRepositoryImpl
import com.example.stablewaifusionalpha.data.repository.PromptTemplatesRepositoryImpl
import com.example.stablewaifusionalpha.data.repository.QualitiesRepositoryImpl
import com.example.stablewaifusionalpha.data.repository.WebSocketRepositoryImpl
import com.example.stablewaifusionalpha.data.repository.WorkflowRepositoryImpl
import com.example.stablewaifusionalpha.domain.repository.PromptTemplatesRepository
import com.example.stablewaifusionalpha.domain.repository.QualitiesRepository
import com.example.stablewaifusionalpha.domain.repository.GenerateImageRepository
import com.example.stablewaifusionalpha.domain.repository.OnboardingRepository
import com.example.stablewaifusionalpha.domain.repository.WebSocketRepository
import com.example.stablewaifusionalpha.domain.repository.WorkflowRepository
import com.example.stablewaifusionalpha.domain.usecase.GetWorkflowUseCase
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
    fun provideGenerateImageRepository(
        apiService: ApiService,
        workflowUseCase: GetWorkflowUseCase,
        clientIdProvider: ClientIdProvider
    ): GenerateImageRepository = GenerateImageRepositoryImpl(
        apiService,
        workflowUseCase,
        clientIdProvider
    )

    @Singleton
    @Provides
    fun provideQualitiesRepository(
        @ApplicationContext context: Context
    ): QualitiesRepository = QualitiesRepositoryImpl(context)

    @Singleton
    @Provides
    fun providePromptTemplatesRepository(
        @ApplicationContext context: Context
    ): PromptTemplatesRepository = PromptTemplatesRepositoryImpl(context)

    @Singleton
    @Provides
    fun provideWorkflowRepository(
        @ApplicationContext context: Context
    ): WorkflowRepository = WorkflowRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideWebSocketRepository(
        webSocketClient: WebSocketClient,
        clientIdProvider: ClientIdProvider
    ): WebSocketRepository = WebSocketRepositoryImpl(webSocketClient, clientIdProvider)

    @Provides
    @Singleton
    fun provideOnboardingRepository(
        appPreferences: AppPreferences
    ): OnboardingRepository = OnboardingRepositoryImpl(appPreferences)

}
