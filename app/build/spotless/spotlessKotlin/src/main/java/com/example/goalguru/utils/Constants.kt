
package com.example.goalguru.utils

object Constants {
    // Database
    const val DATABASE_NAME = "goal_guru_database"

    // SharedPreferences
    const val PREFS_NAME = "goal_guru_prefs"
    const val PREFS_USER_ONBOARDED = "user_onboarded"
    const val PREFS_NOTIFICATIONS_ENABLED = "notifications_enabled"

    // Firebase Collections
    const val COLLECTION_USERS = "users"
    const val COLLECTION_GOALS = "goals"
    const val COLLECTION_TASKS = "tasks"

    // Notification
    const val NOTIFICATION_CHANNEL_ID = "goal_reminders"
    const val NOTIFICATION_CHANNEL_NAME = "Goal Reminders"

    // Work Manager
    const val WORK_TAG_GOAL_REMINDER = "goal_reminder"

    // Date Formats
    const val DATE_FORMAT_DISPLAY = "MMM dd, yyyy"
    const val DATE_FORMAT_API = "yyyy-MM-dd"

    // Limits
    const val MAX_GOAL_TITLE_LENGTH = 50
    const val MAX_GOAL_DESCRIPTION_LENGTH = 200
    const val MAX_TASKS_PER_GOAL = 20
}
