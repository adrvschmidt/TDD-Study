package br.com.schmidt.appwithtdd.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PlaylistDetailsViewModel(
    private val service: PlaylistDetailsService
): ViewModel() {

    val loader = MutableLiveData<Boolean>()
    val playlistsDetails: MutableLiveData<Result<PlaylistsDetails>> = MutableLiveData()

    fun getPlaylistDetails(id: String) {
        viewModelScope.launch {
            loader.postValue(true)
            service.fetchPlaylistDetails(id)
                .onEach {
                    loader.postValue(false)
                }
                .collect {
                    playlistsDetails.postValue(it)
                }
        }
    }

}
