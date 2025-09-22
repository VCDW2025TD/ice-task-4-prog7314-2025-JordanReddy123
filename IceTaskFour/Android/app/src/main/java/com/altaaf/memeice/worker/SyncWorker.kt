package com.altaaf.memeice.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.altaaf.memeice.data.Repository

class SyncWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val repo = Repository(applicationContext)
        return try {
            repo.trySyncPending()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}