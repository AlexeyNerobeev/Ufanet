package com.example.ufanet.feature_app.data.repositoryImplementation

import com.example.ufanet.feature_app.data.dto.ProfileDto
import com.example.ufanet.feature_app.data.mappers.toModel
import com.example.ufanet.feature_app.data.supabase.Connect.supabase
import com.example.ufanet.feature_app.domain.models.Profile
import com.example.ufanet.feature_app.domain.models.User
import com.example.ufanet.feature_app.domain.repository.ProfileRepository
import com.example.ufanet.feature_app.domain.usecase.LoadUserIdUseCase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns

class ProfileRepositoryImpl(
    private val loadUserIdUseCase: LoadUserIdUseCase
): ProfileRepository {
    override suspend fun addNewProfile(companyName: String, phone: String, email: String) {
        val profile = ProfileDto(
            company_name = companyName,
            phone = phone,
            email = email,
            status = "клиент",
            user_id = loadUserIdUseCase.invoke()
        )
        supabase.from("profile").insert(profile)
    }

    override suspend fun getProfile(): Profile {
        return supabase.postgrest["profile"].select(
            columns = Columns.list(
                "id",
                "company_name",
                "status",
                "email",
                "phone"
            )
        ){
            filter {
                and {
                    eq("user_id", loadUserIdUseCase.invoke())
                }
            }
        }.decodeSingle<ProfileDto>().toModel()
    }

    override suspend fun updateProfile(companyName: String, phone: String) {
        val profile = ProfileDto(company_name = companyName, phone = phone)
        supabase.from("profile").update(profile) {
            filter {
                and {
                    eq("user_id", loadUserIdUseCase.invoke())
                }
            }
        }
    }

    override suspend fun getProfileStatus(): Profile {
        return supabase.postgrest["profile"].select(
            columns = Columns.list(
                "status"
            )
        ){
            filter {
                and {
                    eq("user_id", getUserId())
                }
            }
        }.decodeSingle<ProfileDto>().toModel()
    }

    override suspend fun getCompanyInfo(): Profile {
        return supabase.postgrest["profile"].select(
            columns = Columns.list(
                "company_name",
                "phone"
            )
        ){
            filter {
                eq("user_id", loadUserIdUseCase.invoke())
            }
        }.decodeSingle<ProfileDto>().toModel()
    }

    suspend fun getUserId(): String {
        supabase.auth.awaitInitialization()
        return supabase.auth.currentSessionOrNull()?.user?.id ?: ""
    }
}