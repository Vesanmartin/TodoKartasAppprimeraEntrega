package com.example.login001v.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "credentials")
data class Credential(
    @PrimaryKey
    val username: String,
    val password: String
) {
    companion object {
        val Admin = Credential(username = "admin", password = "123")
    }
}
