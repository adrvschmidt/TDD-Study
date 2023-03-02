package br.com.schmidt.appwithtdd.details

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistDetailsService  @Inject constructor(
    private val api: PlaylistDetailsApi
) {

    suspend fun fetchPlaylistDetails(id: String) : Flow<Result<PlaylistsDetails>> {
        return flow {
            emit(Result.success(api.fetchPlaylistDetails(id)))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }
}