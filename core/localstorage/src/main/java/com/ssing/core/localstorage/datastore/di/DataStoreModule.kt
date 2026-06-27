package com.ssing.core.localstorage.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private const val TOKEN_PREFERENCE_NAME = "token_preference"

    private val Context.tokenDataStore by preferencesDataStore(TOKEN_PREFERENCE_NAME)

    @TokenDataStore
    @Provides
    @Singleton
    fun provideTokenDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.tokenDataStore
}
