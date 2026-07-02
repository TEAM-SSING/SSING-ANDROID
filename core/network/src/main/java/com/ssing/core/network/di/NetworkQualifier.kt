package com.ssing.core.network.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SocketOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WithTokenOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WithoutTokenOkHttpClient
