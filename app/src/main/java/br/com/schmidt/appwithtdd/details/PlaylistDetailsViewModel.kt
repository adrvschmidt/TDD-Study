package br.com.schmidt.appwithtdd.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlaylistDetailsViewModel: ViewModel() {

    val playlistsDetails: MutableLiveData<Result<PlaylistsDetails>> = MutableLiveData()

    fun getPlaylistDetails(id: String) {
        TODO("Not yet implemented")
    }

}
