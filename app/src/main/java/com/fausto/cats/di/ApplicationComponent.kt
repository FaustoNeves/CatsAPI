package com.fausto.cats.di

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fausto.cats.R
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
internal class ApplicationComponent : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}