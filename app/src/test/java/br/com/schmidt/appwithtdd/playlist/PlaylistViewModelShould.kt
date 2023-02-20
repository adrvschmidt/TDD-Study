package br.com.schmidt.appwithtdd.playlist

import br.com.schmidt.appwithtdd.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.verify

class PlaylistViewModelShould: BaseUnitTest() {

    private val repository: PlaylistRepository = mock()
    private val playlists = mock<List<Playlist>>()
    private val expected = Result.success(playlists)
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistsFromRepository() = runBlockingTest {
        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }.then {
            val viewModel = PlaylistViewModel(repository)
            viewModel.playlists.value
            runBlocking {
                verify(repository, times(1)).getPlaylists()
            }
        }
    }

    @Test
    fun emitsPlaylistsFromRepository() = runBlockingTest {
        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }.then {
            val viewModel = PlaylistViewModel(repository)
            assertEquals(expected, viewModel.playlists.value)
        }
    }

    @Test
    fun emitErrorWhenREceiverError(){
        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(Result.failure(exception))
                }
            )
        }.then {
            val viewModel = PlaylistViewModel(repository)
            assertEquals(exception, viewModel.playlists.value!!.exceptionOrNull())
        }
    }

    private fun getPlaylistViewModel(): PlaylistViewModel {
        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        return PlaylistViewModel(repository)
    }
}