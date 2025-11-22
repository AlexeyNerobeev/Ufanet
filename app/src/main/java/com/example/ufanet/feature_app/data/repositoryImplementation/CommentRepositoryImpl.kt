package com.example.ufanet.feature_app.data.repositoryImplementation

import com.example.ufanet.feature_app.data.supabase.Connect.supabase
import com.example.ufanet.feature_app.domain.models.Comment
import com.example.ufanet.feature_app.domain.repository.CommentRepository
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns

class CommentRepositoryImpl: CommentRepository {
    override suspend fun addComment(commentText: String) {
        val comment = Comment(comment_text = commentText, author_id = getUserId())
        supabase.postgrest["comments"].insert(comment)
    }

    override suspend fun getComments(applicationId: Int): List<String> {
        return supabase.postgrest["comments"].select(
            columns = Columns.list(
                "comment_text"
            )
        ){
            filter {
                and {
                    eq("application_id", applicationId)
                }
            }
        }.decodeList<String>()
    }

    private suspend fun getUserId(): String {
        supabase.auth.awaitInitialization()
        return supabase.auth.currentUserOrNull()?.id ?: ""
    }
}