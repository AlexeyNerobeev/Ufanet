package com.example.ufanet.feature_app.presentation.Comments

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CommentsVM: ViewModel() {
    private val _state = mutableStateOf(CommentsState())
    val state: State<CommentsState> = _state

    fun onEvent(event: CommentsEvent){
        when(event){
            is CommentsEvent.ApplicationStatusChangeCompleted -> {
                _state.value = state.value.copy(
                    applicationStatus = "Выполнена"
                )
            }
            is CommentsEvent.ApplicationStatusChangeAccepted -> {
                _state.value = state.value.copy(
                    applicationStatus = "Принята"
                )
            }
            is CommentsEvent.ApplicationStatusChangeNotAccepted -> {
                _state.value = state.value.copy(
                    applicationStatus = "Не принята"
                )
            }
            is CommentsEvent.AddNewComment -> {
                _state.value = state.value.copy(
                    newComment = true
                )
            }
            is CommentsEvent.EnteredCommentDescription -> {
                _state.value = state.value.copy(
                    commentDescription = event.value
                )
            }
        }
    }
}