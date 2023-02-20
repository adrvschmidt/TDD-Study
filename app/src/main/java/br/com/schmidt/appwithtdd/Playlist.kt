package br.com.schmidt.appwithtdd

data class Playlist(
    val id: String,
    val name: String,
    val category: String,
    val image: Int = R.mipmap.playlist
)