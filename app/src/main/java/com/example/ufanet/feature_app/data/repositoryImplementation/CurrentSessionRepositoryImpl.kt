package com.example.ufanet.feature_app.data.repositoryImplementation

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.example.ufanet.feature_app.domain.repository.CurrentSessionRepository

class CurrentSessionRepositoryImpl(context: Context): CurrentSessionRepository {
    private val prefs = context.getSharedPreferences("CurrentSession", Context.MODE_PRIVATE)
    override fun saveUser(id: String, status: String) {
        try {
            prefs.edit { putString("userId", id) }
            prefs.edit{ putString("userStatus", status) }
        } catch (e: Exception) {
            Log.e("saveUser", e.message.toString())
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

    override fun loadUserStatus(): String {
        try {
            val status = prefs.getString("userStatus", null)
            return status ?: ""
        } catch (e: Exception) {
            Log.e("loadUserStatus", e.message.toString())
            return ""
        }
    }

    override fun deleteUserId() {
        try {
            prefs.edit{clear()}
        } catch (e: Exception) {
            Log.e("deleteUserId", e.message.toString())
        }
    }
}