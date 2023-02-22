package br.com.schmidt.appwithtdd.playlist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class PlaylistRepository @Inject constructor(
    private val service: PlaylistService
) {

    suspend fun getPlaylists(): Flow<Result<List<Playlist>>> =
        service.fetchPlaylists()
}
