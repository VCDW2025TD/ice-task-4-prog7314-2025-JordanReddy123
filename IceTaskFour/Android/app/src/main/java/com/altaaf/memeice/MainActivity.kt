package com.altaaf.memeice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.altaaf.memeice.api.ApiClient
import com.altaaf.memeice.api.MemeService
import com.altaaf.memeice.model.Meme
import com.altaaf.memeice.ui.MemeAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var service: MemeService
    private lateinit var rv: RecyclerView
    private val adapter = MemeAdapter()

        // Schedule periodic sync (15 min minimum on most devices)
        val request = PeriodicWorkRequestBuilder<com.altaaf.memeice.worker.SyncWorker>(java.time.Duration.ofMinutes(15))
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "meme_sync",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv = findViewById(R.id.rvMemes)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        service = ApiClient.instance

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = service.getMemes()
                withContext(Dispatchers.Main) {
                    adapter.submitList(res.items)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}