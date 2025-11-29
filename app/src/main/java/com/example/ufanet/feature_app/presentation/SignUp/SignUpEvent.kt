package com.example.ufanet.feature_app.presentation.SignUp

sealed class SignUpEvent {
    data class EnteredCompanyName(val value: String): SignUpEvent()
    data class EnteredPhone(val value: String): SignUpEvent()
    data class EnteredEmail(val value: String): SignUpEvent()
    data class EnteredPassword(val value: String): SignUpEvent()
    data object Registration: SignUpEvent()
    data object VisualTransformation: SignUpEvent()
    data object ExceptionClear: SignUpEvent()
    data object ShowProgressIndicator: SignUpEvent()
}