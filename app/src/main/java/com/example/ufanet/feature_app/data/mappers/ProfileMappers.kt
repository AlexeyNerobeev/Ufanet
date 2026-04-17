package com.example.ufanet.feature_app.data.mappers

import com.example.ufanet.feature_app.data.dto.ProfileDto
import com.example.ufanet.feature_app.domain.models.Profile

internal fun Profile.toDto() = ProfileDto(
    id = id,
    company_name = company_name,
    phone = phone,
    email = email,
    status = status,
    user_id = user_id,
    role_id = role_id
)

internal fun ProfileDto.toModel() = Profile(
    id = id,
    company_name = company_name,
    phone = phone,
    email = email,
    status = status,
    user_id = user_id,
    role_id = role_id
)