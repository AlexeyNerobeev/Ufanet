package com.example.ufanet.feature_app.data.repositoryImplementation

import android.util.Log
import com.example.ufanet.App
import com.example.ufanet.feature_app.data.dto.ApplicationDto
import com.example.ufanet.feature_app.data.mappers.toModel
import com.example.ufanet.feature_app.data.supabase.Connect.supabase
import com.example.ufanet.feature_app.domain.models.Application
import com.example.ufanet.feature_app.domain.repository.ApplicationRepository
import com.example.ufanet.feature_app.domain.usecase.LoadUserIdUseCase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns

class ApplicationRepositoryImpl(
    private val loadUserIdUseCase: LoadUserIdUseCase
) : ApplicationRepository {
    override suspend fun addApplication(
        companyName: String,
        address: String,
        phone: String,
        description: String
    ) {
        val application = ApplicationDto(
            company_name = companyName,
            address = address,
            phone = phone,
            description = description,
            user_id = loadUserIdUseCase.invoke(),
            status = "Не принята"
        )
        supabase.postgrest["applications"].insert(application)
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
                    eq("user_id", loadUserIdUseCase.invoke())
                }
            }
        }.decodeList<ApplicationDto>().map { it.toModel() }
    }

    override suspend fun removeApplication(id: Int) {
        supabase.from("applications").delete {
            filter {
                eq("id", id)
                eq(column = "user_id", loadUserIdUseCase.invoke())
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
        ) {
            filter {
                and {
                    eq("id", id)
                    eq("user_id", loadUserIdUseCase.invoke())
                }
            }
        }.decodeSingle<ApplicationDto>().toModel()
    }

    override suspend fun updateApplication(
        id: Int,
        companyName: String,
        address: String,
        phone: String,
        description: String
    ) {
        val application = ApplicationDto(
            company_name = companyName,
            address = address,
            phone = phone,
            description = description
        )
        supabase.from("applications").update(application) {
            filter {
                and {
                    eq("id", id)
                    eq("user_id", loadUserIdUseCase.invoke())
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
                "status",
                "comments_count"
            )
        ).decodeList<ApplicationDto>().map { it.toModel() }
    }

    override suspend fun getApplicationStatus(applicationId: Int): Application {
        return supabase.postgrest["applications"].select(
            columns = Columns.list(
                "status"
            )
        ) {
            filter {
                and {
                    eq("id", applicationId)
                }
            }
        }.decodeSingle<ApplicationDto>().toModel()
    }

    override suspend fun updateApplicationStatus(applicationId: Int, status: String) {
        val application = ApplicationDto(status = status)
        supabase.from("applications").update(application) {
            filter {
                and {
                    eq("id", applicationId)
                }
            }
        }
    }

    override suspend fun updateCommentsCount(applicationId: Int, commentsCount: Int) {
        val commentsCount = ApplicationDto(comments_count = commentsCount)
        supabase.from("applications").update(commentsCount) {
            filter {
                and {
                    eq("id", applicationId)
                }
            }
        }
    }

    override suspend fun getFilterApplication(
        searchText: String,
        column: String,
        status: String,
        commentsCount: Int
    ): List<Application> {
        return supabase.postgrest["applications"].select(
            columns = Columns.list(
                "id",
                "company_name",
                "phone",
                "description",
                "address",
                "status",
                "comments_count"
            )
        ) {
            filter {
                and {
                    if (searchText.isNotEmpty()) {
                        ilike(column, "%${searchText}%")
                    }
                    if (status.isNotEmpty()) {
                        eq("status", status)
                    }
                    if (commentsCount >= 0) {
                        if (commentsCount > 2) {
                            gt("comments_count", 2)
                        } else {
                            eq("comments_count", commentsCount)
                        }
                    }
                }
            }
        }.decodeList<ApplicationDto>().map { it.toModel() }
    }

    override suspend fun getApplicationMapInfo(applicationId: Int): Application {
        return supabase.postgrest["applications"].select(
            columns = Columns.list(
                "company_name",
                "address",
                "phone"
            )
        ) {
            filter {
                eq("id", applicationId)
            }
        }.decodeSingle<ApplicationDto>().toModel()
    }
}
