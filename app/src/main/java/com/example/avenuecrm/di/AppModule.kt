package com.example.avenuecrm.di

import android.content.Context
import com.example.avenuecrm.data.encryption.CryptoManager
import com.example.avenuecrm.data.serializers.CredentialsCryptoSerializer
import com.example.avenuecrm.data.preferences.abstraction.DataStoreRepository
import com.example.avenuecrm.data.preferences.implementation.DataStoreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCryptoManager(): CryptoManager = CryptoManager()

    @Provides
    @Singleton
    fun provideCredentialsSerializer(cryptoManager: CryptoManager): CredentialsCryptoSerializer = CredentialsCryptoSerializer(cryptoManager)

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context,
        serializer: CredentialsCryptoSerializer
    ): DataStoreRepository = DataStoreRepositoryImpl(context, serializer)


}