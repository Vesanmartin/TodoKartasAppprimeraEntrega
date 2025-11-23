package com.example.login001v.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.login001v.data.model.Credential

@Dao
interface CredentialDao {

    @Query("SELECT * FROM credentials WHERE username = :username LIMIT 1")
    suspend fun getCredential(username: String): Credential?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCredential(credential: Credential)
}
