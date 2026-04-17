package com.example.ufanet.feature_app.data.mappers

import com.example.ufanet.feature_app.data.dto.UserDto
import com.example.ufanet.feature_app.domain.models.User

internal fun User.toDto() = UserDto(
    id = id,
    email = email
)

internal fun UserDto.toModel() = User(
    id = id,
    email = email
)