package com.altaaf.memeice.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PendingMemeDao {
    @Query("SELECT * FROM pending_memes ORDER BY createdAt ASC")
    suspend fun getAll(): List<PendingMemeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: PendingMemeEntity)

    @Delete
    suspend fun delete(item: PendingMemeEntity)

    @Query("DELETE FROM pending_memes WHERE localId = :id")
    suspend fun deleteById(id: Long)
}