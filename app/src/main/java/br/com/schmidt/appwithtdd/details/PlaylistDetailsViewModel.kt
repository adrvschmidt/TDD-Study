package br.com.schmidt.appwithtdd.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlaylistDetailsViewModel(
    private val service: PlaylistDetailsService
): ViewModel() {

    val playlistsDetails: MutableLiveData<Result<PlaylistsDetails>> = MutableLiveData()

    fun getPlaylistDetails(id: String) {
        viewModelScope.launch {
            service.fetchPlaylistDetails(id)
                .collect {
                    playlistsDetails.postValue(it)
                }
        }
    }

}
