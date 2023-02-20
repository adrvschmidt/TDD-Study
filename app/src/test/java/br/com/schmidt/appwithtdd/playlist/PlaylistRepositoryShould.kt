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
    private val playlists = mock<List<Playlist>>()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistFromService() = runTest {

        val repository = PlaylistRepository(service)
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

    private fun mockFailureCase(): PlaylistRepository {
        runBlocking {
            whenever(service.fetchPlaylists()).thenReturn(
                flow {
                    emit(Result.failure(exception))
                }
            )
        }
        val repository = PlaylistRepository(service)
        return repository
    }

    private fun mockSuccessfullCase(): PlaylistRepository {
        runBlocking {
            whenever(service.fetchPlaylists()).thenReturn(
                flow {
                    emit(Result.success(playlists))
                }
            )
        }
        val repository = PlaylistRepository(service)
        return repository
    }
}