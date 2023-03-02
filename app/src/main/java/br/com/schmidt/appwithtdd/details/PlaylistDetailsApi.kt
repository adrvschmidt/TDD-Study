package br.com.schmidt.appwithtdd.details

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

interface PlaylistDetailsApi {

    @GET("getdetails/{id}")
    suspend fun fetchPlaylistDetails(@Path("id") id: String): PlaylistsDetails
}
