
package com.example.goalguru.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [],
    version = 1,
    exportSchema = false
)
@TypeConverters()
abstract class GoalGuruDatabase : RoomDatabase() {
    // TODO: Add your DAOs here when you create entities
}
