package com.example.ufanet.feature_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ufanet.feature_app.data.entities.ApplicationEntity
import com.example.ufanet.feature_app.domain.models.Application

@Dao
interface ApplicationDao {
    @Query("SELECT * FROM applications")
    suspend fun getAllCacheApplications(): List<ApplicationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAcheApplications(applications: List<ApplicationEntity>)

    @Query("""
        SELECT * FROM applications
        WHERE 
            (:searchText IS NULL OR 
                company_name LIKE '%' || :searchText || '%' OR
                phone LIKE '%' || :searchText || '%' OR
                description LIKE '%' || :searchText || '%' OR
                address LIKE '%' || :searchText || '%'
            )
        AND (:status IS NULL OR status = :status)
        AND (
            :commentsCount < 0 OR
            (:commentsCount > 2 AND comments_count > 2) OR
            (:commentsCount <= 2 AND comments_count = :commentsCount)
        )
    """)
    suspend fun getFilterCacheApplications(
        searchText: String?,
        status: String?,
        commentsCount: Int
    ): List<ApplicationEntity>
}