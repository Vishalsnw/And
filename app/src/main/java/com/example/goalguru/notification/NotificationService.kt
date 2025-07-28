package com.example.goalguru.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.goalguru.R
import com.example.goalguru.data.model.UserSettings
import com.example.goalguru.data.repository.GoalRepository
import com.example.goalguru.data.repository.UserRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.util.Calendar
import java.util.concurrent.TimeUnit

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val goalRepository: GoalRepository,
    private val userRepository: UserRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val userSettings = userRepository.getUserSettings()
            if (!userSettings.notificationsEnabled) return Result.success()

            val goals = goalRepository.goals.first()
            val incompleteGoals = goals.filter { it.progress < 1.0f }

            if (incompleteGoals.isNotEmpty()) {
                showNotification(userSettings, incompleteGoals.size)
            }

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun showNotification(userSettings: UserSettings, incompleteCount: Int) {
        val context = applicationContext
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            "goal_reminders",
            "Goal Reminders",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val message = generateNotificationMessage(userSettings, incompleteCount)

        val notification = NotificationCompat.Builder(context, "goal_reminders")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("GoalGuru Reminder")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }

    private fun generateNotificationMessage(
        userSettings: UserSettings,
        incompleteCount: Int
    ): String {
        val name = if (userSettings.name.isNotEmpty()) userSettings.name else "Champion"

        return when (userSettings.notificationStyle) {
            "Harsh" -> when {
                userSettings.age < 25 ->
                    "Hey $name! Still slacking? You have $incompleteCount incomplete goals. Time to hustle! 💪"

                userSettings.gender == "Female" ->
                    "Queen $name, your goals are waiting! $incompleteCount tasks need your attention. Show them who's boss! 👑"

                else ->
                    "Bro $name, seriously? $incompleteCount goals are incomplete. Stop making excuses and get to work! 🔥"
            }

            else -> when {
                userSettings.age < 25 ->
                    "Hi $name! You've got this! $incompleteCount goals are waiting for your magic touch ✨"

                userSettings.gender == "Female" ->
                    "Hello beautiful $name! Time to shine with your $incompleteCount pending goals 🌟"

                else ->
                    "Hey $name! Ready to conquer those $incompleteCount goals? You're doing great! 🎯"
            }
        }
    }
}

object NotificationScheduler {
    fun scheduleDaily9PMNotification(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val notificationWork = PeriodicWorkRequestBuilder<NotificationWorker>(
            1, TimeUnit.DAYS
        )
            .setConstraints(constraints)
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "daily_goal_reminder",
            ExistingPeriodicWorkPolicy.REPLACE,
            notificationWork
        )
    }

    private fun calculateInitialDelay(): Long {
        val now = System.currentTimeMillis()
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 21)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            if (timeInMillis <= now) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }
        return calendar.timeInMillis - now
    }
}
