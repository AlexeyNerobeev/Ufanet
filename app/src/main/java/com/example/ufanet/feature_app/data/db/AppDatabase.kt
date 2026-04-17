package com.example.ufanet.feature_app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ufanet.feature_app.data.dao.ApplicationDao
import com.example.ufanet.feature_app.data.entities.ApplicationEntity

@Database(entities = [ApplicationEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun applicationDao(): ApplicationDao
}