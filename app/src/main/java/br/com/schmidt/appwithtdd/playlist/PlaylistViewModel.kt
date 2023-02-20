package br.com.schmidt.appwithtdd.playlist

import androidx.lifecycle.*

class PlaylistViewModel(
    private val repository: PlaylistRepository
): ViewModel() {

    val playlists = liveData<Result<List<Playlist>>> {
        emitSource(repository.getPlaylists().asLiveData())
    }
}
