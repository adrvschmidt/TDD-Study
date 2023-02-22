package br.com.schmidt.appwithtdd.playlist

import br.com.schmidt.appwithtdd.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class PlaylistRepositoryShould: BaseUnitTest() {

    private val service: PlaylistService = mock()
    private val mapper: PlaylistMapper = mock()
    private val playlists = mock<List<Playlist>>()
    private val playlistRaw = mock<List<PlaylistRaw>>()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistFromService() = runTest {

        val repository = PlaylistRepository(service, mapper)
        repository.getPlaylists()

        verify(service, times(1)).fetchPlaylists()
    }

    @Test
    fun emitPlaylistFromService() = runTest {
        val repository = mockSuccessfullCase()
        assertEquals(playlists, repository.getPlaylists().first().getOrNull())
    }

    @Test
    fun propagateErrors() = runTest {
        val repository = mockFailureCase()
        assertEquals(exception, repository.getPlaylists().first().exceptionOrNull())
    }

    @Test
    fun delegateBusinessLogicToMapper() = runTest {
        val repository = mockSuccessfullCase()
        repository.getPlaylists().first()
        verify(mapper, times(1)).invoke(playlistRaw)
    }

    private fun mockFailureCase(): PlaylistRepository {
        runBlocking {
            whenever(service.fetchPlaylists()).thenReturn(
                flow {
                    emit(Result.failure<List<PlaylistRaw>>(exception))
                }
            )
        }
        return PlaylistRepository(service, mapper)
    }

    private fun mockSuccessfullCase(): PlaylistRepository {
        runBlocking {
            whenever(service.fetchPlaylists()).thenReturn(
                flow {
                    emit(Result.success(playlistRaw))
                }
            )
        }

        whenever(mapper.invoke(playlistRaw)).thenReturn(playlists)
        return PlaylistRepository(service, mapper)
    }
}