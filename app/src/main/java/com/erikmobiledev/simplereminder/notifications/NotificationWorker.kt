package com.erikmobiledev.simplereminder.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.compose.ui.res.stringResource
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.erikmobiledev.cronoapp.R
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class NotificationWorker(context: Context, params: WorkerParameters): Worker(context, params) {

    override fun doWork(): Result {
        showBasicNotification()
        return Result.success()
    }

    private fun showBasicNotification(){
        val title = applicationContext.getString(R.string.notification_title)
        val desc = applicationContext.getString(R.string.notification_desc)

        val notification = NotificationCompat.Builder(applicationContext,"123")
            .setContentTitle(title)
            .setContentText(desc)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.notification_icon)
            .build()
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }

    companion object{
        fun releaseNotification(context: Context, delayInMillis: Long) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresCharging(false)
                .setRequiresBatteryNotLow(false)
                .build()

            val notificationWork = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setConstraints(constraints)
                .setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS)
                .build()

            WorkManager.getInstance(context).enqueue(notificationWork)
        }
    }
}