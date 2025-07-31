package com.example.goalguru.firebase

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseConfig {

    val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    lateinit var analytics: FirebaseAnalytics
        private set

    fun initialize(context: Context) {
        // Ensure Firebase is initialized
        FirebaseApp.initializeApp(context)
        analytics = FirebaseAnalytics.getInstance(context)
    }
}
