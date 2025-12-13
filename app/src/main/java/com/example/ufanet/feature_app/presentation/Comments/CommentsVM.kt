package com.example.ufanet.feature_app.presentation.Comments

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ufanet.feature_app.domain.usecase.AddCommentUseCase
import com.example.ufanet.feature_app.domain.usecase.GetApplicationStatusUseCase
import com.example.ufanet.feature_app.domain.usecase.GetCommentsForApplicationUseCase
import com.example.ufanet.feature_app.domain.usecase.UpdateApplicationCommentsCountUseCase
import com.example.ufanet.feature_app.domain.usecase.UpdateApplicationStatusUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentsVM(
    private val getApplicationStatusUseCase: GetApplicationStatusUseCase,
    private val getCommentsForApplicationUseCase: GetCommentsForApplicationUseCase,
    private val addCommentUseCase: AddCommentUseCase,
    private val updateApplicationStatusUseCase: UpdateApplicationStatusUseCase,
    private val updateApplicationCommentsCountUseCase: UpdateApplicationCommentsCountUseCase
) : ViewModel() {
    private val _state = mutableStateOf(CommentsState())
    val state: State<CommentsState> = _state

    fun onEvent(event: CommentsEvent) {
        when (event) {
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

            is CommentsEvent.GetApplicationId -> {
                _state.value = state.value.copy(
                    applicationId = event.value
                )
            }

            is CommentsEvent.GetAllInfo -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val status =
                            getApplicationStatusUseCase.invoke(state.value.applicationId).status
                        _state.value = state.value.copy(
                            currentApplicationStatus = status,
                            applicationStatus = status,
                            commentsList = getCommentsForApplicationUseCase.invoke(state.value.applicationId)
                        )
                    } catch (ex: Exception) {
                        Log.e("supabase", ex.message.toString())
                    }
                }
            }

            is CommentsEvent.WriteNewComment -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        if (state.value.commentDescription.isNotEmpty() && state.value.commentDescription != null) {
                            addCommentUseCase.invoke(
                                commentText = state.value.commentDescription,
                                applicationId = state.value.applicationId
                            )
                            updateApplicationCommentsCountUseCase.invoke(
                                applicationId = state.value.applicationId,
                                commentsCount = state.value.commentsList.size + 1
                            )
                            _state.value = state.value.copy(
                                isComplete = true
                            )
                        }
                    } catch (ex: Exception) {
                        Log.e("supabase", ex.message.toString())
                    }
                }
            }

            is CommentsEvent.UpdateStatus -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        if (state.value.applicationStatus != state.value.currentApplicationStatus) {
                            updateApplicationStatusUseCase.invoke(
                                applicationId = state.value.applicationId,
                                status = state.value.applicationStatus
                            )
                            _state.value = state.value.copy(
                                isComplete = true
                            )
                        }
                    } catch (ex: Exception) {
                        Log.e("supabase", ex.message.toString())
                    }
                }
            }

            is CommentsEvent.ShowProcessIndicator -> {
                _state.value = state.value.copy(
                    progressIndicator = true
                )
            }

            is CommentsEvent.ErrorClear -> {
                _state.value = state.value.copy(
                    error = ""
                )
            }

            is CommentsEvent.ShowError -> {
                _state.value = state.value.copy(
                    error = "Измените статус заявки или напишите комментарий!"
                )
            }

            is CommentsEvent.ShowNotSelectItems -> {
                _state.value = state.value.copy(
                    showNotSelectItems = true
                )
            }
        }
    }
}