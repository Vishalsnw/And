package com.example.goalguru

import android.app.Application
import android.util.Log
import androidx.startup.AppInitializer
import androidx.work.Configuration
import androidx.work.WorkManagerInitializer
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics

class GoalGuruApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()

        // ✅ Disable auto-initialization of WorkManager (prevents crash)
        AppInitializer.getInstance(this).disable(WorkManagerInitializer::class.java)

        // ✅ Initialize Firebase
        FirebaseApp.initializeApp(this)

        // ✅ Enable Firebase Crashlytics logging and crash reporting
        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.setCrashlyticsCollectionEnabled(true)
        crashlytics.sendUnsentReports()
        crashlytics.log("GoalGuruApplication started")
    }

    // ✅ Provide custom WorkManager configuration here
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(Log.INFO)
            .build()
    }
}
