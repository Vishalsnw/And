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
import com.example.goalguru.data.repository.GoalRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull
import java.util.Calendar

@HiltWorker
class HumorousNotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val goalRepository: GoalRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val todaysTasks = goalRepository.getTodaysTasks().firstOrNull().orEmpty()
            val incompleteTasks = todaysTasks.filter { !it.isCompleted }

            when {
                currentHour == 9 && incompleteTasks.isNotEmpty() -> {
                    showMotivationalNotification(incompleteTasks.size)
                }
                currentHour == 21 && incompleteTasks.isNotEmpty() -> {
                    showRoastNotification(incompleteTasks.size)
                }
                currentHour in 12..18 && incompleteTasks.isNotEmpty() -> {
                    showHumorousReminder(incompleteTasks.size)
                }
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    private fun showMotivationalNotification(taskCount: Int) {
        val messages = listOf(
            "Rise and grind! You've got $taskCount tasks waiting to be conquered! 💪",
            "Good morning, goal crusher! Time to tackle those $taskCount tasks! 🌟",
            "Your future self is counting on you! $taskCount tasks to complete today! 🚀"
        )
        showNotification("Morning Motivation", messages.random())
    }

    private fun showRoastNotification(taskCount: Int) {
        val messages = listOf(
            "Still got $taskCount tasks left? The day called, it wants its productivity back! 😏",
            "Those $taskCount tasks aren't going to complete themselves while you scroll social media! 📱",
            "Procrastination level: Expert. Task completion level: Needs work. $taskCount tasks remaining! ⏰"
        )
        showNotification("Reality Check", messages.random())
    }

    private fun showHumorousReminder(taskCount: Int) {
        val messages = listOf(
            "Your tasks are getting lonely! They miss being completed. $taskCount still waiting! 😢",
            "Breaking news: $taskCount tasks still unchecked. Scientists baffled! 📰",
            "Task update: $taskCount items are still hoping for your attention! 🤞"
        )
        showNotification("Friendly Reminder", messages.random())
    }

    private fun showNotification(title: String, message: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "humorous_reminders",
                "Humorous Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "humorous_reminders")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}