package com.example.ufanet.feature_app.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "applications")
data class ApplicationEntity(
    @PrimaryKey
    val id: Int,
    val company_name: String,
    val address: String,
    val phone: String,
    val description: String,
    val user_id: String,
    val status: String,
    val comments_count: Int
)
