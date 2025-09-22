package com.altaaf.memeice.model

data class MemePost(
    val userId: String,
    val title: String,
    val imageUrl: String,
    val caption: String? = "",
    val tags: List<String>? = emptyList(),
    val source: String? = ""
)