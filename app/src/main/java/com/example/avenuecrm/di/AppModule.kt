package com.example.avenuecrm.di

import android.content.Context
import com.example.avenuecrm.CryptoManager
import com.example.avenuecrm.UserInformationSerializer
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
    fun provideUserInformationSerializer(cryptoManager: CryptoManager): UserInformationSerializer = UserInformationSerializer(cryptoManager)

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context,
        serializer: UserInformationSerializer
    ): DataStoreRepository = DataStoreRepositoryImpl(context, serializer)


}