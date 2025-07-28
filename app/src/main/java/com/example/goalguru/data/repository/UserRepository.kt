
package com.example.goalguru.data.repository

import com.example.goalguru.data.model.UserSettings
import com.example.goalguru.firebase.FirebaseConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor() {

    private val firestore = FirebaseConfig.firestore
    private val auth = FirebaseConfig.auth

    fun getUserSettings(): Flow<UserSettings> = callbackFlow {
        val userId = auth.currentUser?.uid ?: "default"
        val listener = firestore.collection("users")
            .document(userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                val settings = snapshot?.toObject(UserSettings::class.java) ?: UserSettings()
                trySend(settings)
            }
        
        awaitClose { listener.remove() }
    }

    suspend fun updateUserSettings(settings: UserSettings) {
        val userId = auth.currentUser?.uid ?: "default"
        firestore.collection("users")
            .document(userId)
            .set(settings)
            .await()
    }
}
