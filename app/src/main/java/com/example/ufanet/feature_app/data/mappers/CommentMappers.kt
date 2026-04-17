package com.example.ufanet.feature_app.data.mappers

import com.example.ufanet.feature_app.data.dto.CommentDto
import com.example.ufanet.feature_app.domain.models.Comment

internal fun Comment.toDto() = CommentDto(
    id = id,
    application_id = application_id,
    author_id = author_id,
    comment_text = comment_text
)

internal fun CommentDto.toModel() = Comment(
    id = id,
    application_id = application_id,
    author_id = author_id,
    comment_text = comment_text
)