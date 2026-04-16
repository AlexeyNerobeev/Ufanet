package com.example.ufanet

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application(){
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("feb87948-5df3-440e-9122-356c208fa993")
        MapKitFactory.initialize(this)
    }
}
// {
//    override fun onCreate() {
//        super.onCreate()
//
//        startKoin {
//            androidContext(applicationContext)
//            androidLogger(level = Level.DEBUG)
//            modules(
//                moduleVM, moduleAuth, moduleApplications, moduleProfile, moduleComment,
//                moduleCurrentSession
//            )
//        }
//    }
//}