package br.com.schmidt.appwithtdd.playlist

import br.com.schmidt.appwithtdd.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.verify

class PlaylistViewModelShould: BaseUnitTest() {

    private val repository: PlaylistRepository = mock()
    private val playlists = mock<List<Playlist>>()
    private val expected = Result.success(playlists)
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistsFromRepository() = runTest {
        whenever(repository.getPlaylists()).thenReturn(
            flow {
                emit(expected)
            }
        )
        val viewModel = PlaylistViewModel(repository)
        viewModel.playlists.observeForever {
            runBlocking {
                verify(repository, times(1)).getPlaylists()
            }
        }
    }

    @Test
    fun emitsPlaylistsFromRepository() {
        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        val viewModel = PlaylistViewModel(repository)
        viewModel.playlists.observeForever {
            assertEquals(expected, viewModel.playlists.value)
        }
    }

    @Test
    fun emitErrorWhenREceiverError() {
        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(Result.failure<List<Playlist>>(exception))
                }
            )
        }
        val viewModel = PlaylistViewModel(repository)
        viewModel.playlists.observeForever {
            assertEquals(exception, viewModel.playlists.value!!.exceptionOrNull())
        }
    }

    @Test
    fun showSpinnerWhileLoading() {
        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        val viewModel = PlaylistViewModel(repository)
        viewModel.loader.value.runCatching {
            viewModel.playlists.value
            print(viewModel.loader.value)
            assertEquals(true, viewModel.loader.value)
        }
    }

    @Test
    fun closeLoaderAfterPlaylistsLoad(){
        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        val viewModel = PlaylistViewModel(repository)
        viewModel.loader.observeForever {
            viewModel.playlists.value
            assertEquals(false, it)
        }
    }

    @Test
    fun closeLoaderAfterError(){
        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(Result.failure<List<Playlist>>(exception))
                }
            )
        }
        val viewModel = PlaylistViewModel(repository)
        viewModel.loader.observeForever {
            viewModel.playlists.value
            assertEquals(false, it)
        }
    }
}