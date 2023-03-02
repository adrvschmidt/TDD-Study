package br.com.schmidt.appwithtdd.details

import br.com.schmidt.appwithtdd.playlist.PlaylistAPI
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val client = OkHttpClient()
val idlingResource = OkHttp3IdlingResource.create("okhttp", client)
@Module
@InstallIn(FragmentComponent::class)
class PlaylistDetailsModule {

        @Provides
        fun playlistDetailsApi(): PlaylistDetailsApi =
            Retrofit.Builder()
                .baseUrl("https://cf5425f3-3d75-4159-81ce-3e5059f9fe5a.mock.pstmn.io/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(PlaylistDetailsApi::class.java)
    }