package br.com.schmidt.appwithtdd.playlist

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(FragmentComponent::class)
class PlaylistModule {

    @Provides
    fun playlistApi(retrofit: Retrofit): PlaylistAPI =
        retrofit.create(PlaylistAPI::class.java)

    @Provides
    fun retrofit(): Retrofit = Retrofit.Builder()
            .baseUrl("https://171e1301-5e74-4de4-8444-6cab6b8fe20a.mock.pstmn.io/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}