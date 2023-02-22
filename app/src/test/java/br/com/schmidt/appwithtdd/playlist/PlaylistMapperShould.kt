package br.com.schmidt.appwithtdd.playlist

import br.com.schmidt.appwithtdd.utils.BaseUnitTest
import org.junit.Assert.assertEquals
import br.com.schmidt.appwithtdd.R
import org.junit.Test

class PlaylistMapperShould: BaseUnitTest() {

    private val playlistRaw = PlaylistRaw("1", "name", "category")
    private val playlistRawRock = PlaylistRaw("1", "name", "rock")
    private val mapper = PlaylistMapper()
    private val playlists = mapper(listOf(playlistRaw))
    private val playlistsRock = mapper(listOf(playlistRawRock))
    private val playlist = playlists[0]
    private val playlistRock = playlistsRock[0]

    @Test
    fun keepSameID() {
        assertEquals(playlistRaw.id, playlist.id)
    }

    @Test
    fun keepSameName() {
        assertEquals(playlistRaw.name, playlist.name)
    }

    @Test
    fun keepSameCategory() {
        assertEquals(playlistRaw.category, playlist.category)
    }

    @Test
    fun mapDefaultImageWhenNotRock() {
        assertEquals(R.mipmap.playlist, playlist.image)
    }

    @Test
    fun mapRockImageWhenRockCategory() {
        assertEquals(R.mipmap.rock, playlistRock.image)
    }
}