package com.mmfsin.estereotipia.base

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import com.google.android.gms.ads.MobileAds
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        MobileAds.initialize(this) {}

        getFCMToken()
        disableNightMode()
    }

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) println("**** FCM **** ${it.result}")
            else println("FCM -> no token")
        }
    }

    private fun disableNightMode() = setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
}