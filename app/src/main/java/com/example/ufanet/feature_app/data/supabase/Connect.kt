package com.example.ufanet.feature_app.data.supabase

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object Connect {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://uzponhmapfsetwhnxwcs.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InV6cG9uaG1hcGZzZXR3aG54d2NzIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTc1ODczNjIsImV4cCI6MjA3MzE2MzM2Mn0.a3v4KTngfXhnDAgKxJxisNHVGfMOlD-Yhi1CYBPtsBE"
    ){
        install(Auth)
        install(Postgrest)
    }
}