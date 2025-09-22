package com.altaaf.memeice.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pending_memes")
data class PendingMemeEntity(
    @PrimaryKey(autoGenerate = true) val localId: Long = 0,
    val userId: String,
    val title: String,
    val imageUrl: String,
    val caption: String? = "",
    val tags: String? = "",
    val source: String? = "",
    val createdAt: Long = System.currentTimeMillis()
)