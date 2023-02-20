package br.com.schmidt.appwithtdd.playlist

import retrofit2.http.GET

interface PlaylistAPI {

    @GET("get")
    suspend fun fetchAllPlaylists() : List<Playlist>
}
