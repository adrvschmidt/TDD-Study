package br.com.schmidt.appwithtdd.playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.schmidt.appwithtdd.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A fragment representing a list of Items.
 */
class PlaylistFragment : Fragment() {

    lateinit var viewModel: PlaylistViewModel
    lateinit var viewModelFactory: PlaylistViewModelFactory

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://171e1301-5e74-4de4-8444-6cab6b8fe20a.mock.pstmn.io/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(PlaylistAPI::class.java)
    private val service = PlaylistService(api)
    private val repository = PlaylistRepository(service)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_playlist, container, false)
        setupViewModel()

        viewModel.playlists.observe(this as LifecycleOwner) { playlists ->
            if (playlists.getOrNull() != null)
                setupList(view, playlists.getOrNull()!!)
            else {
                //TODO
            }
        }

        return view
    }

    private fun setupList(view: View, playlist: List<Playlist>) {
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyPlaylistRecyclerViewAdapter(playlist)
        }
    }

    private fun setupViewModel() {
        viewModelFactory = PlaylistViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[PlaylistViewModel::class.java]
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PlaylistFragment().apply {}
    }
}