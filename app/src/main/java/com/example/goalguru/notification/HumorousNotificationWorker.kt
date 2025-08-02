
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
import java.util.Date

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
            "🌟 Rise and grind! You've got $taskCount tasks waiting to be conquered!",
            "☕ Time to turn that coffee into productivity! $taskCount tasks are calling your name!",
            "🎯 Your future self is counting on present you! Let's tackle those $taskCount tasks!",
            "🚀 Houston, we have $taskCount missions to complete today!"
        )
        
        sendNotification(
            "Daily Motivation 💪",
            messages.random(),
            "daily_motivation"
        )
    }

    private fun showHumorousReminder(taskCount: Int) {
        val messages = listOf(
            "👀 Your tasks are getting lonely... They've been waiting all day!",
            "🐌 At this pace, snails are faster than your progress today!",
            "📱 Your phone has more battery than your motivation right now!",
            "🍕 Even pizza delivery is faster than your task completion!",
            "⏰ Time is ticking... and so are your uncompleted tasks!"
        )
        
        sendNotification(
            "Friendly Reminder 😅",
            messages.random(),
            "humorous_reminder"
        )
    }

    private fun showRoastNotification(taskCount: Int) {
        val roastMessages = listOf(
            "🔥 ROAST TIME: You had ALL day and still have $taskCount tasks left? Really?",
            "😤 Your procrastination game is STRONG! $taskCount tasks are crying right now!",
            "🙄 Netflix: 8 hours, Tasks completed: 0. Math doesn't lie!",
            "💀 RIP productivity. Cause of death: $taskCount incomplete tasks.",
            "🤡 Congratulations! You've unlocked the 'Professional Procrastinator' achievement!",
            "😂 Your tasks called... they want a different owner!",
            "🍿 Your tasks had more entertainment watching you avoid them all day!"
        )
        
        sendNotification(
            "Time for Some Truth 🔥",
            roastMessages.random(),
            "roast_notification"
        )
    }

    private fun sendNotification(title: String, message: String, channelId: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                title,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
