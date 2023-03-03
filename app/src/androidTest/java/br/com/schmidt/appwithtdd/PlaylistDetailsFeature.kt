package br.com.schmidt.appwithtdd

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import br.com.schmidt.appwithtdd.playlist.idlingResource
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import org.hamcrest.Matchers
import org.hamcrest.Matchers.endsWith
import org.junit.Test

class PlaylistDetailsFeature: BaseUITest() {

    @Test
    fun displaysPlaylistNameAndDetails() {
        navigateToPlayListDetails(0)

        assertDisplayed(R.id.playlist_name_details)
        assertDisplayed(R.id.playlist_details)
        Thread.sleep(3000)
        onView(withText(endsWith("Hard Rock Cafe"))).check(matches(isDisplayed()))
      //  onView(withText(endsWith("Rock your senses with this timeless signature vibe list. \\n\\n • Poison \\n • You shook me all" +
      //          " night \\n • Zombie \\n • Rock'n Me \\n • Thunderstruck \\n " +
      //          "• I Hate Myself for Loving you \\n • Crazy \\n • Knockin' on Heavens Door"))).check(matches(isDisplayed()))
    }

    @Test
    fun displaysLoaderWhileFetchingThePlaylistDetails(){
        navigateToPlayListDetails(0)

        IdlingRegistry.getInstance().unregister(idlingResource)

        assertDisplayed(R.id.details_loader)
    }

    @Test
    fun hidesLoader() {
        navigateToPlayListDetails(0)

        IdlingRegistry.getInstance().unregister(idlingResource)
        Thread.sleep(3000)
        assertNotDisplayed(R.id.details_loader)
    }

    @Test
    fun displayNetworkErrorWhenNotConnected() {
        navigateToPlayListDetails(1)
        assertDisplayed(R.string.generic_error)
    }

    @Test
    fun hideDisplayNetworkErrorMessageWhenNotConnected() {
        navigateToPlayListDetails(1)
        Thread.sleep(3500)
        assertNotDisplayed(R.string.generic_error)
    }

    private fun navigateToPlayListDetails(position: Int) {
        onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.playlist_image),
                ViewMatchers.isDescendantOfA(
                    nthChildOf(
                        ViewMatchers.withId(R.id.playlists_list),
                        position
                    )
                )
            )
        ).perform(ViewActions.click())
    }
}