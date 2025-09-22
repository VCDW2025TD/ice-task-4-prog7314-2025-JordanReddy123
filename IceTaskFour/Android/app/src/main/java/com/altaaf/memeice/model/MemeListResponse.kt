package com.altaaf.memeice.model

data class MemeListResponse(
    val items: List<Meme>,
    val total: Int,
    val page: Int,
    val pages: Int
)