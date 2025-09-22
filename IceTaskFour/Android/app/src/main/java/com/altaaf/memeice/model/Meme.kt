package com.altaaf.memeice.model

data class Meme(
    val _id: String,
    val userId: String,
    val title: String,
    val imageUrl: String,
    val caption: String?,
    val tags: List<String>?,
    val source: String?,
    val createdAt: String?,
    val updatedAt: String?
)