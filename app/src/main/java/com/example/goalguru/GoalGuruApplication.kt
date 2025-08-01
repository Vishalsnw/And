
package com.example.goalguru

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import com.example.goalguru.firebase.FirebaseConfig
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GoalGuruApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase first
        FirebaseApp.initializeApp(this)
        
        // Initialize our Firebase config
        FirebaseConfig.initialize(this)

        // Enable Firebase Crashlytics logging and crash reporting
        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.setCrashlyticsCollectionEnabled(true)
        crashlytics.sendUnsentReports()
        crashlytics.log("GoalGuruApplication started")

        Log.i("GoalGuru", "App started successfully with Firebase initialized")
    }

    // Provide WorkManager configuration correctly
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.INFO)
            .build()
}
