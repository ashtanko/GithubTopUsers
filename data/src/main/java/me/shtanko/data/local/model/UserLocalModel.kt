package me.shtanko.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class UserLocalModel(
    @PrimaryKey var id: Int,
    val login: String,
    val avatarUrl: String
)