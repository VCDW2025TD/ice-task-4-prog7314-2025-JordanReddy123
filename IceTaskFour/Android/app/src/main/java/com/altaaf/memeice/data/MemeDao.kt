package com.altaaf.memeice.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MemeDao {
    @Query("SELECT * FROM memes ORDER BY createdAt DESC")
    suspend fun getAll(): List<MemeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<MemeEntity>)

    @Query("DELETE FROM memes")
    suspend fun clear()
}