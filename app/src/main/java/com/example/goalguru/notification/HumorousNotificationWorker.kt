
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
import com.example.goalguru.data.model.DailyTask
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull
import java.util.Calendar

@HiltWorker
class HumorousNotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val goalRepository: GoalRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val todaysTasks = getTodaysTasks()
            val incompleteTasks = todaysTasks.filter { !it.isCompleted }

            when {
                currentHour in 8..11 && incompleteTasks.isNotEmpty() -> {
                    showMorningMotivation(incompleteTasks.size)
                }
                currentHour in 12..17 && incompleteTasks.isNotEmpty() -> {
                    showAfternoonReminder(incompleteTasks.size)
                }
                currentHour in 18..21 && incompleteTasks.isNotEmpty() -> {
                    showEveningNudge(incompleteTasks.size)
                }
                incompleteTasks.isEmpty() -> {
                    showCompletionCelebration()
                }
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    private suspend fun getTodaysTasks(): List<DailyTask> {
        return try {
            val today = java.time.LocalDate.now()
            goalRepository.getAllGoals().firstOrNull()?.let { goals ->
                goals.flatMap { goal ->
                    goalRepository.getTasksForGoal(goal.id).firstOrNull() ?: emptyList()
                }
            } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun showMorningMotivation(taskCount: Int) {
        val messages = listOf(
            "Rise and grind! You've got $taskCount tasks waiting to be conquered! ☀️",
            "Good morning, goal crusher! Time to show those $taskCount tasks who's boss! 💪",
            "Coffee's ready, goals are waiting! Let's tackle those $taskCount tasks! ☕"
        )
        showNotification("Morning Motivation", messages.random())
    }

    private fun showAfternoonReminder(taskCount: Int) {
        val messages = listOf(
            "Afternoon check-in: $taskCount tasks are still waiting for your magic touch! ✨",
            "Post-lunch power hour! Those $taskCount tasks won't complete themselves! 🚀",
            "Halfway through the day, but not through your goals! $taskCount to go! 📈"
        )
        showNotification("Afternoon Reminder", messages.random())
    }

    private fun showEveningNudge(taskCount: Int) {
        val messages = listOf(
            "Evening warrior! $taskCount tasks before you can relax. You've got this! 🌙",
            "The day isn't over yet! $taskCount tasks are cheering for your attention! 📣",
            "Almost bedtime, but not goal time! $taskCount tasks left to tackle! 💤"
        )
        showNotification("Evening Nudge", messages.random())
    }

    private fun showCompletionCelebration() {
        val messages = listOf(
            "🎉 All tasks complete! You're officially awesome today!",
            "🏆 Goal achieved! Time to celebrate your success!",
            "✅ Perfect day! All tasks conquered like a true champion!"
        )
        showNotification("Mission Accomplished!", messages.random())
    }

    private fun showNotification(title: String, message: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        createNotificationChannel(notificationManager)
        
        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Goal Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Humorous reminders for your goals"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "goal_reminders"
    }

    @dagger.assisted.AssistedFactory
    interface Factory {
        fun create(context: Context, params: WorkerParameters): HumorousNotificationWorker
    }
}
