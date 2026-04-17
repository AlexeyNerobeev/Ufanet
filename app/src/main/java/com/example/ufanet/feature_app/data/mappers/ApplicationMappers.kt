package com.example.ufanet.feature_app.data.mappers

import com.example.ufanet.feature_app.data.dto.ApplicationDto
import com.example.ufanet.feature_app.data.entities.ApplicationEntity
import com.example.ufanet.feature_app.domain.models.Application

internal fun Application.toDto() = ApplicationDto(
    id = id,
    company_name = company_name,
    phone = phone,
    description = description,
    user_id = user_id,
    address = address,
    status = status,
    comments_count = comments_count
)

internal fun ApplicationDto.toModel() = Application(
    id = id,
    company_name = company_name,
    phone = phone,
    description = description,
    user_id = user_id,
    address = address,
    status = status,
    comments_count = comments_count
)

internal fun Application.toDao() = ApplicationEntity(
    id = id,
    company_name = company_name,
    address = address,
    phone = phone,
    description = description,
    user_id = user_id,
    status = status,
    comments_count = comments_count
)

internal fun ApplicationEntity.toModel() = Application(
    id = id,
    company_name = company_name,
    phone = phone,
    description = description,
    user_id = user_id,
    address = address,
    status = status,
    comments_count = comments_count
)