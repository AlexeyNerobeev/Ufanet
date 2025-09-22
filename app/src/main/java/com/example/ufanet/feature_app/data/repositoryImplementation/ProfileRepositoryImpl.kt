package com.example.ufanet.feature_app.data.repositoryImplementation

import com.example.ufanet.feature_app.data.supabase.Connect.supabase
import com.example.ufanet.feature_app.domain.models.Profile
import com.example.ufanet.feature_app.domain.repository.ProfileRepository
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns

class ProfileRepositoryImpl: ProfileRepository {
    override suspend fun addNewProfile(companyName: String, phone: String, email: String) {
        val profile = Profile(company_name = companyName, phone = phone, email = email, status = "клиент", user_id = getUserId())
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
                    eq("user_id", getUserId())
                }
            }
        }.decodeSingle<Profile>()
    }

    private suspend fun getUserId(): String {
        supabase.auth.awaitInitialization()
        return supabase.auth.currentUserOrNull()?.id ?: ""
    }

    override suspend fun updateProfile(companyName: String, phone: String) {
        val profile = Profile(company_name = companyName, phone = phone)
        supabase.from("profile").update(profile) {
            filter {
                and {
                    eq("user_id", getUserId())
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
        }.decodeSingle<Profile>()
    }
}