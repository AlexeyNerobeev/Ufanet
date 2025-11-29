package com.example.ufanet.feature_app.data.repositoryImplementation

import com.example.ufanet.feature_app.data.supabase.Connect.supabase
import com.example.ufanet.feature_app.domain.models.Comment
import com.example.ufanet.feature_app.domain.repository.CommentRepository
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns

class CommentRepositoryImpl: CommentRepository {
    override suspend fun addComment(commentText: String, applicationId: Int) {
        val comment = Comment(comment_text = commentText, author_id = getUserId(), application_id = applicationId)
        supabase.postgrest["comments"].insert(comment)
    }

    override suspend fun getComments(applicationId: Int): List<Comment> {
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
        }.decodeList<Comment>()
    }

    private suspend fun getUserId(): String {
        supabase.auth.awaitInitialization()
        return supabase.auth.currentUserOrNull()?.id ?: ""
    }
}