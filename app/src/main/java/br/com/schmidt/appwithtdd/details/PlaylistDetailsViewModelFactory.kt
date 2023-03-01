package br.com.schmidt.appwithtdd.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlaylistDetailsViewModelFactory: ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlaylistDetailsViewModel() as T
    }
}
