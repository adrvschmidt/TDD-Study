package br.com.schmidt.appwithtdd

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.schmidt.appwithtdd.utils.MainCoroutineScopeRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify

class PlaylistViewModelShould {

    @get:Rule
    var coroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: PlaylistRepository = mock()
    private val playlists = mock<List<Playlist>>()
    private val expected = Result.success(playlists)

    @Test
    fun getPlaylistsFromRepository() = runBlockingTest {
        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        val viewModel = PlaylistViewModel(repository)
        viewModel.playlist.value

        verify(repository, times(1)).getPlaylists()
    }

    @Test
    fun emitsPlaylistsFromRepository() = runBlockingTest {
        whenever(repository.getPlaylists()).thenReturn(
            flow {
                emit(expected)
            }
        )
        val viewModel = PlaylistViewModel(repository)
        assertEquals(expected, viewModel.playlist.value)
    }
}