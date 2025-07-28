
package com.example.goalguru.data.repository

import com.example.goalguru.data.model.UserSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor() {
    
    private var userSettings = UserSettings()
    
    suspend fun saveUserSettings(settings: UserSettings) {
        userSettings = settings
    }
    
    suspend fun getUserSettings(): UserSettings {
        return userSettings
    }
}
package com.example.goalguru.data.repository

import com.example.goalguru.data.model.UserSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor() {
    
    suspend fun getUserSettings(): UserSettings {
        // TODO: Implement user settings retrieval
        return UserSettings()
    }
    
    suspend fun updateUserSettings(settings: UserSettings) {
        // TODO: Implement user settings update
    }
}
