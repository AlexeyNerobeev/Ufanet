package com.example.ufanet.feature_app.presentation.Comments

sealed class CommentsEvent {
    data object ApplicationStatusChangeAccepted: CommentsEvent()
    data object ApplicationStatusChangeNotAccepted: CommentsEvent()
    data object ApplicationStatusChangeCompleted: CommentsEvent()
    data object AddNewComment: CommentsEvent()
    data class EnteredCommentDescription(val value: String): CommentsEvent()
    data class GetAllInfo(val value: Int): CommentsEvent()
}