package br.com.schmidt.appwithtdd.details

import br.com.schmidt.appwithtdd.playlist.PlaylistViewModel
import br.com.schmidt.appwithtdd.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.internal.verification.VerificationModeFactory.times

class PlaylistsDetailsViewModelShould : BaseUnitTest() {

    lateinit var viewModel: PlaylistDetailsViewModel
    private val id = "1"
    private val service: PlaylistDetailsService = mock()
    private val playlistDetails: PlaylistsDetails = mock()
    private val expected = Result.success(playlistDetails)
    private val exception = RuntimeException("Something went wrong")
    private val error = Result.failure<PlaylistsDetails>(exception)

    @Test
    fun getPlaylistDetailsFromService() = runTest {
        viewModel = PlaylistDetailsViewModel(service)
        viewModel.getPlaylistDetails(id)
        viewModel.playlistsDetails.value
        verify(service, times(1)).fetchPlaylistDetails(id)
    }

    @Test
    fun emitPlaylistDetailsFromService() = runTest {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(expected)
            }
        )
        viewModel = PlaylistDetailsViewModel(service)
        viewModel.getPlaylistDetails(id)
        assertEquals(expected, viewModel.playlistsDetails.value)
    }

    @Test
    fun emitErrorWheServiceFails() = runTest {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(error)
            }
        )
        viewModel = PlaylistDetailsViewModel(service)
        viewModel.getPlaylistDetails(id)
        assertEquals(error, viewModel.playlistsDetails.value)
    }

    @Test
    fun showSpinnerWhileLoading() = runTest {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(expected)
            }
        )
        viewModel = PlaylistDetailsViewModel(service)
        viewModel.loader.runCatching {
            print(viewModel.loader.value)
            assertEquals(true, viewModel.loader.value)
        }
    }

    @Test
    fun closeLoaderAfterPlaylistsLoad() = runTest {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(expected)
            }
        )
        viewModel = PlaylistDetailsViewModel(service)
        viewModel.getPlaylistDetails(id)
        viewModel.loader.observeForever {
            print(it)
            assertEquals(false, it)
        }
    }
}