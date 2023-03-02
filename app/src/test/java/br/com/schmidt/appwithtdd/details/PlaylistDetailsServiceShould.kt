package br.com.schmidt.appwithtdd.details

import br.com.schmidt.appwithtdd.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class PlaylistDetailsServiceShould: BaseUnitTest() {

    private val id = "1"
    private lateinit var service: PlaylistDetailsService
    private val api: PlaylistDetailsApi = mock()
    private val playlistDetails: PlaylistsDetails = mock()
    private val exception = RuntimeException("Damn backend developers again 500!")
    private val error = Result.failure<PlaylistsDetails>(exception)

    @Test
    fun fetchPlaylistDetailsFromAPI() = runTest {
        service = PlaylistDetailsService(api)
        service.fetchPlaylistDetails(id).single()

        verify(api, times(1)).fetchPlaylistDetails(id)
    }

    @Test
    fun convertValuesToFlowResultAndEmitThem() = runTest {
        whenever(api.fetchPlaylistDetails(id)).thenReturn(
            playlistDetails
        )

        service = PlaylistDetailsService(api)
        assertEquals(Result.success(playlistDetails), service.fetchPlaylistDetails(id).single())
    }

    @Test
    fun emitErrorResultWhenNetworkFails() = runTest {
        whenever(api.fetchPlaylistDetails(id)).thenThrow(exception)
        service = PlaylistDetailsService(api)

        assertEquals("Something went wrong", service.fetchPlaylistDetails(id)
            .single().exceptionOrNull()?.message)
    }
}