package com.example.ufanet.feature_app.data.repositoryImplementation

import com.example.ufanet.feature_app.data.dto.CommentDto
import com.example.ufanet.feature_app.data.mappers.toModel
import com.example.ufanet.feature_app.data.supabase.Connect.supabase
import com.example.ufanet.feature_app.domain.models.Comment
import com.example.ufanet.feature_app.domain.repository.CommentRepository
import com.example.ufanet.feature_app.domain.usecase.LoadUserIdUseCase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns

class CommentRepositoryImpl(
    private val loadUserIdUseCase: LoadUserIdUseCase
): CommentRepository {
    override suspend fun addComment(commentText: String, applicationId: Int) {
        val comment = CommentDto(
            comment_text = commentText,
            author_id = loadUserIdUseCase.invoke(),
            application_id = applicationId
        )
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
        }.decodeList<CommentDto>().map { it.toModel() }
    }
}