package com.altaaf.memeice.data

import android.content.Context
import com.altaaf.memeice.api.ApiClient
import com.altaaf.memeice.model.Meme
import com.altaaf.memeice.model.MemePost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val context: Context) {
    private val db = AppDatabase.get(context)
    private val service = ApiClient.instance

    suspend fun loadMemes(): List<Meme> = withContext(Dispatchers.IO) {
        return@withContext try {
            val resp = service.getMemes()
            val mapped = resp.items.map {
                MemeEntity(
                    id = it._id,
                    userId = it.userId,
                    title = it.title,
                    imageUrl = it.imageUrl,
                    caption = it.caption,
                    tags = it.tags?.joinToString(","),
                    source = it.source,
                    createdAt = it.createdAt,
                    updatedAt = it.updatedAt
                )
            }
            db.memeDao().clear()
            db.memeDao().upsertAll(mapped)
            resp.items
        } catch (e: Exception) {
            // offline fallback
            db.memeDao().getAll().map {
                Meme(
                    _id = it.id,
                    userId = it.userId,
                    title = it.title,
                    imageUrl = it.imageUrl,
                    caption = it.caption,
                    tags = it.tags?.split(",")?.filter { s -> s.isNotBlank() },
                    source = it.source,
                    createdAt = it.createdAt,
                    updatedAt = it.updatedAt
                )
            }
        }
    }

    suspend fun enqueueUpload(post: MemePost) = withContext(Dispatchers.IO) {
        val pending = PendingMemeEntity(
            userId = post.userId,
            title = post.title,
            imageUrl = post.imageUrl,
            caption = post.caption,
            tags = post.tags?.joinToString(","),
            source = post.source
        )
        db.pendingMemeDao().insert(pending)
    }

    suspend fun trySyncPending() = withContext(Dispatchers.IO) {
        val all = db.pendingMemeDao().getAll()
        for (p in all) {
            try {
                service.postMeme(
                    MemePost(
                        userId = p.userId,
                        title = p.title,
                        imageUrl = p.imageUrl,
                        caption = p.caption,
                        tags = p.tags?.split(",")?.filter { it.isNotBlank() },
                        source = p.source
                    )
                )
                db.pendingMemeDao().deleteById(p.localId)
            } catch (_: Exception) {
                // remain in queue
            }
        }
    }
}