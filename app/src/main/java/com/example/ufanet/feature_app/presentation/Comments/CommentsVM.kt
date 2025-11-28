package com.example.ufanet.feature_app.presentation.Comments

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ufanet.feature_app.domain.usecase.GetApplicationStatusUseCase
import com.example.ufanet.feature_app.domain.usecase.GetCommentsForApplicationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentsVM(
    private val getApplicationStatusUseCase: GetApplicationStatusUseCase,
    private val getCommentsForApplicationUseCase: GetCommentsForApplicationUseCase
): ViewModel() {
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
            is CommentsEvent.GetAllInfo -> {
                viewModelScope.launch(Dispatchers.IO){
                    try {
                        _state.value = state.value.copy(
                            applicationStatus = getApplicationStatusUseCase.invoke(event.value).status,
                            commentsList = getCommentsForApplicationUseCase.invoke(event.value)
                        )
                    } catch (ex: Exception){
                        Log.e("supabase", ex.message.toString())
                    }
                }
            }
        }
    }
}