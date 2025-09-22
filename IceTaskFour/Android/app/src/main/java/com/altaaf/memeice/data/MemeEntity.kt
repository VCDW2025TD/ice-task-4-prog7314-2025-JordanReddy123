package com.altaaf.memeice.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memes")
data class MemeEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val title: String,
    val imageUrl: String,
    val caption: String?,
    val tags: String?, // comma-separated
    val source: String?,
    val createdAt: String?,
    val updatedAt: String?
)