package com.example.ufanet.feature_app.data.repositoryImplementation

import com.example.ufanet.feature_app.data.supabase.Connect.supabase
import com.example.ufanet.feature_app.domain.models.Application
import com.example.ufanet.feature_app.domain.repository.ApplicationRepository
import com.example.ufanet.feature_app.presentation.Applications.ApplicationsVM
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import java.util.Locale.filter

class ApplicationRepositoryImpl : ApplicationRepository {
    override suspend fun addApplication(
        companyName: String,
        address: String,
        phone: String,
        description: String
    ) {
        val application = Application(
            company_name = companyName, address = address,
            phone = phone, description = description, user_id = getUserId(), status = "Не принята"
        )
        supabase.postgrest["applications"].insert(application)
    }

    private suspend fun getUserId(): String {
        supabase.auth.awaitInitialization()
        return supabase.auth.currentUserOrNull()?.id ?: ""
    }

    override suspend fun getApplications(): List<Application> {
        return supabase.postgrest["applications"].select(
            columns = Columns.list(
                "id",
                "company_name",
                "phone",
                "description",
                "address",
                "status"
            )
        ) {
            filter {
                and {
                    eq("user_id", getUserId())
                }
            }
        }.decodeList<Application>()
    }

    override suspend fun removeApplication(id: Int) {
        supabase.from("applications").delete {
            filter {
                eq("id", id)
                eq(column = "user_id", getUserId())
            }
        }
    }

    override suspend fun getApplicationForUpdate(id: Int): Application {
        return supabase.postgrest["applications"].select(
            columns = Columns.list(
                "id",
                "company_name",
                "phone",
                "description",
                "address"
            )
        ){
            filter{
                and {
                    eq("id", id)
                    eq("user_id", getUserId())
                }
            }
        }.decodeSingle<Application>()
    }

    override suspend fun updateApplication(
        id: Int,
        companyName: String,
        address: String,
        phone: String,
        description: String
    ) {
        val application = Application(company_name = companyName,
            address = address,
            phone = phone,
            description = description)
        supabase.from("applications").update(application) {
            filter{
                and{
                    eq("id", id)
                    eq("user_id", getUserId())
                }
            }
        }
    }

    override suspend fun getAllApplications(): List<Application> {
        return supabase.postgrest["applications"].select(
            columns = Columns.list(
                "id",
                "company_name",
                "address",
                "phone",
                "description",
                "status"
            )
        ).decodeList<Application>()
    }

    override suspend fun getApplicationStatus(applicationId: Int): Application {
        return supabase.postgrest["applications"].select(
            columns = Columns.list(
                "status"
            )
        ){
            filter {
                and {
                    eq("id", applicationId)
                }
            }
        }.decodeSingle<Application>()
    }
}
