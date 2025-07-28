package com.example.goalguru.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.goalguru.MainActivity
import com.example.goalguru.R
import com.example.goalguru.data.model.UserSettings
import com.example.goalguru.data.repository.GoalRepository
import com.example.goalguru.data.repository.UserRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

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

            val goals = goalRepository.getAllGoals().first()
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

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "goal_reminders")
            .setContentTitle("Goal Reminder")
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }

    private fun generateNotificationMessage(userSettings: UserSettings, incompleteCount: Int): String {
        return when {
            incompleteCount == 1 -> "You have 1 incomplete goal. Keep going!"
            incompleteCount > 1 -> "You have $incompleteCount incomplete goals. Stay focused!"
            else -> "Great job! All your goals are complete!"
        }
    }

    @dagger.assisted.AssistedFactory
    interface Factory {
        fun create(context: Context, params: WorkerParameters): NotificationWorker
    }
}
