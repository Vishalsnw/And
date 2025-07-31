package com.example.goalguru.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
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
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val goalRepository: GoalRepository,
    private val userRepository: UserRepository,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val userSettings = userRepository.getUserSettings().firstOrNull()

            if (userSettings == null || !userSettings.notificationsEnabled) {
                return Result.success()
            }

            val goals = goalRepository.getAllGoals().firstOrNull().orEmpty()
            val incompleteGoals = goals.filter { it.progress < 1.0f }

            if (incompleteGoals.isNotEmpty()) {
                showNotification(incompleteGoals.size)
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    private fun showNotification(incompleteCount: Int) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "goal_reminders",
                "Goal Reminders",
                NotificationManager.IMPORTANCE_DEFAULT,
            )
            notificationManager.createNotificationChannel(channel)
        }

        val message = generateNotificationMessage(incompleteCount)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        val notification = NotificationCompat.Builder(context, "goal_reminders")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Goal Reminder")
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }

    private fun generateNotificationMessage(incompleteCount: Int): String {
        return when (incompleteCount) {
            1 -> "You have 1 incomplete goal. Keep going!"
            else -> "You have $incompleteCount incomplete goals. Stay focused!"
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(context: Context, params: WorkerParameters): NotificationWorker
    }
}
