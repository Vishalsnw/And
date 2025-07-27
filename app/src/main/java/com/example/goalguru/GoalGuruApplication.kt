package com.example.goalguru

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.WorkManager
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics

class GoalGuruApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // ✅ Initialize Firebase
        FirebaseApp.initializeApp(this)

        // ✅ Enable Firebase Crashlytics logging and crash reporting
        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.setCrashlyticsCollectionEnabled(true)
        crashlytics.sendUnsentReports()
        crashlytics.log("GoalGuruApplication started")

        // ✅ Initialize WorkManager
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.INFO)
            .build()
        WorkManager.initialize(this, config)
    }
}
