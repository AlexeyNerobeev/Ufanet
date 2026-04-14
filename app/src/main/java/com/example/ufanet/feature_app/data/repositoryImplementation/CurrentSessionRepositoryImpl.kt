package com.example.ufanet.feature_app.data.repositoryImplementation

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.example.ufanet.feature_app.domain.repository.CurrentSessionRepository

class CurrentSessionRepositoryImpl(context: Context): CurrentSessionRepository {
    private val prefs = context.getSharedPreferences("CurrentSession", Context.MODE_PRIVATE)
    override fun saveUserId(id: String) {
        try {
            prefs.edit { putString("userId", id) }
        } catch (e: Exception) {
            Log.e("saveUserId", e.message.toString())
        }
    }

    override fun loadUserId(): String {
        try {
            val id = prefs.getString("userId", null)
            return id ?: ""
        } catch (e: Exception) {
            Log.e("loadUserId", e.message.toString())
            return ""
        }
    }
}