package com.example.goalguru.firebase

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

object FirebaseConfig {

    val auth: FirebaseAuth
        get() = Firebase.auth

    val firestore: FirebaseFirestore
        get() = Firebase.firestore

    val analytics: FirebaseAnalytics
        get() = Firebase.analytics

    fun initialize(context: Context) {
        // Firebase is automatically initialized via google-services plugin.
        // You can explicitly initialize Firebase if needed using FirebaseApp.initializeApp(context)
        // FirebaseApp.initializeApp(context) // Uncomment if manual init is ever required
    }
}
