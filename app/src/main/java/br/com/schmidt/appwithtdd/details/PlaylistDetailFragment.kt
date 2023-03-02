package br.com.schmidt.appwithtdd.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import br.com.schmidt.appwithtdd.databinding.FragmentPlaylistDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistDetailFragment : Fragment() {

    lateinit var viewModel: PlaylistDetailsViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistDetailsViewModelFactory

    private lateinit var binding: FragmentPlaylistDetailBinding

    val args: PlaylistDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistDetailBinding.inflate(inflater, container, false)
        val id: String = args.playlistId
        setupViewModel()
        viewModel.getPlaylistDetails(id)
        setupObservers()
        return binding.root
    }


    private fun setupObservers() {
        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            when (loading) {
                true -> binding.detailsLoader.visibility = View.VISIBLE
                else -> binding.detailsLoader.visibility = View.GONE
            }
        }
        viewModel.playlistsDetails.observe(this as LifecycleOwner) { playlistsDetails ->
            if (playlistsDetails.getOrNull() != null)
                setupViewText(playlistsDetails.getOrNull())
            else {
                //TODO
            }
        }
    }

    private fun setupViewText(playlistsDetails: PlaylistsDetails?) {
        playlistsDetails?.let { details ->
            binding.playlistNameDetails.text = details.name
            binding.playlistDetails.text = details.details
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[PlaylistDetailsViewModel::class.java]
    }
}