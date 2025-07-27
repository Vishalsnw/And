
package com.example.goalguru.firebase

import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.analytics
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

object FirebaseConfig {
    
    val auth get() = Firebase.auth
    val firestore get() = Firebase.firestore  
    val analytics get() = Firebase.analytics
    
    fun initialize() {
        // Firebase is automatically initialized by the google-services plugin
        // This is just a centralized access point
    }
}
