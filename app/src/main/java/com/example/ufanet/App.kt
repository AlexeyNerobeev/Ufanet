package com.example.ufanet

import android.app.Application
import com.entrig.sdk.Entrig
import com.entrig.sdk.models.EntrigConfig
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application(){
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("feb87948-5df3-440e-9122-356c208fa993")
        MapKitFactory.initialize(this)
        Firebase.initialize(this)
        Entrig.initialize(this,
            EntrigConfig(apiKey = "sk-proj-1a822ff0-9896d37b38b0fe07746204277a7a42f4446c2154ff5b2334bd5e74f8a3e83fb3")
        )
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