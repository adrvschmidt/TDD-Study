package br.com.schmidt.appwithtdd.playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.schmidt.appwithtdd.R
import br.com.schmidt.appwithtdd.databinding.FragmentPlaylistBinding
import br.com.schmidt.appwithtdd.databinding.PlaylistItemBinding
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistFragment : Fragment() {

    lateinit var viewModel: PlaylistViewModel

    private lateinit var binding: FragmentPlaylistBinding

    @Inject
    lateinit var viewModelFactory: PlaylistViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        setupViewModel()

        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            when(loading){
                true -> binding.loader.visibility = View.VISIBLE
                else -> binding.loader.visibility = View.GONE
            }
        }

        viewModel.playlists.observe(this as LifecycleOwner) { playlists ->
            if (playlists.getOrNull() != null)
                setupList(binding.playlistsList, playlists.getOrNull()!!)
            else {
                //TODO
            }
        }

        return binding.root
    }

    private fun setupList(view: View, playlist: List<Playlist>) {
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyPlaylistRecyclerViewAdapter(playlist) { id ->
                val action =
                    PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistDetailFragment(id)
                findNavController().navigate(action)
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[PlaylistViewModel::class.java]
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PlaylistFragment().apply {}
    }
}