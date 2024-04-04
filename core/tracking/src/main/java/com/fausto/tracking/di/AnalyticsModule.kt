package com.fausto.tracking.di

import android.content.Context
import com.fausto.tracking.analytics.Analytics
import com.fausto.tracking.analytics.AnalyticsImpl
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun providesFirebaseModule(@ApplicationContext context: Context) =
        FirebaseAnalytics.getInstance(context)
}

@Module
@InstallIn(SingletonComponent::class)
interface AnalyticsModule {

    @Binds
    fun bindsAnalyticsModule(analytics: AnalyticsImpl): Analytics
}