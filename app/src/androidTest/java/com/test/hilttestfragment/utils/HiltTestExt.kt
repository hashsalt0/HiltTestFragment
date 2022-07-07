package com.test.hilttestfragment.utils

import androidx.annotation.NavigationRes
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.test.core.app.ActivityScenario
import com.test.hilttestfragment.R


/**
 * Helper method for testing navigation resource file
 * Launches an Hilt Activity (mandatory for hilt application tests) which contains navhostfragment
 * which shows the inflated graph.
 *
 * @param graphId  navigation resource file
 * @param performTest callback for performing test
 */
fun launchNavHostFragmentInHiltContainer(
    @NavigationRes graphId: Int,
    performTest: (controller: NavController) -> Unit
) {
    val navHostFragment: NavHostFragment = NavHostFragment.create(graphId)
    val scenario: ActivityScenario<HiltTestActivity> =
        ActivityScenario.launch(HiltTestActivity::class.java)
    scenario.onActivity { activity: HiltTestActivity ->
        // UI thread
        activity.supportFragmentManager.beginTransaction()
            .add(R.id.fragmentHiltTestContainerView, navHostFragment, "hiltFragmentContainer")
            .setPrimaryNavigationFragment(navHostFragment)
            .commitNow()
    }
    scenario.moveToState(Lifecycle.State.RESUMED)
    performTest(navHostFragment.navController)
}

/**
 * launches fragment inside hilt test activity
 * @param performTest callback for performing test
 * @param fragmentFactory use this to change the default fragment factory for hilt test activity
 */
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentFactory: FragmentFactory?,
    performTest: () -> Unit
) {
    val scenario: ActivityScenario<HiltTestActivity> =
        ActivityScenario.launch(HiltTestActivity::class.java)
    scenario.onActivity { activity: HiltTestActivity ->
        if (fragmentFactory != null) {
            activity.supportFragmentManager.fragmentFactory = fragmentFactory
        }
        val fragment: Fragment =
            activity.supportFragmentManager.fragmentFactory.instantiate(
                Preconditions.checkNotNull(T::class.java.classLoader),
                T::class.java.name
            )
        // UI thread
        activity.supportFragmentManager.beginTransaction()
            .add(R.id.fragmentHiltTestContainerView, fragment, "hiltFragmentContainer")
            .setPrimaryNavigationFragment(fragment)
            .commitNow()
    }
    scenario.moveToState(Lifecycle.State.RESUMED)
    performTest()
}

/**
 * launches fragment inside hilt test activity
 * @param performTest callback for performing test
 */
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    performTest: () -> Unit
) {
    launchFragmentInHiltContainer<T>(null, performTest)
}
