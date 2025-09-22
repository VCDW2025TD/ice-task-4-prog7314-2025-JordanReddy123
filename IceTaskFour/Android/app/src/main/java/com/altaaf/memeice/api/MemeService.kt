package com.altaaf.memeice.api

import com.altaaf.memeice.model.MemeListResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Query

interface MemeService {
    @GET("memes")
    suspend fun getMemes(
        @Query("userId") userId: String? = null,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): MemeListResponse
}

    @POST("memes")
    suspend fun postMeme(@Body body: MemePost): com.altaaf.memeice.model.Meme
