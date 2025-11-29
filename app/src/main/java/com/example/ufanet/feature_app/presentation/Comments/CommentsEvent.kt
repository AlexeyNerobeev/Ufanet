package com.example.ufanet.feature_app.presentation.Comments

sealed class CommentsEvent {
    data object ApplicationStatusChangeAccepted: CommentsEvent()
    data object ApplicationStatusChangeNotAccepted: CommentsEvent()
    data object ApplicationStatusChangeCompleted: CommentsEvent()
    data object AddNewComment: CommentsEvent()
    data class EnteredCommentDescription(val value: String): CommentsEvent()
    data object GetAllInfo: CommentsEvent()
    data class GetApplicationId(val value: Int): CommentsEvent()
    data object WriteNewComment: CommentsEvent()
    data object UpdateStatus: CommentsEvent()
    data object ShowProcessIndicator: CommentsEvent()
    data object ErrorClear: CommentsEvent()
    data object ShowError: CommentsEvent()
}