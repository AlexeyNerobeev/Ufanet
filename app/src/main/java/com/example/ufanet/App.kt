package com.example.ufanet

import android.app.Application
import com.example.ufanet.di.moduleApplications
import com.example.ufanet.di.moduleAuth
import com.example.ufanet.di.moduleComment
import com.example.ufanet.di.moduleProfile
import com.example.ufanet.di.moduleVM
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            androidLogger(level = Level.DEBUG)
            modules(
                moduleVM, moduleAuth, moduleApplications, moduleProfile, moduleComment
            )
        }
    }
}