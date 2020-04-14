package app.storytel.candidate.com.common.command

import androidx.navigation.NavDirections

/**
 * A sealed class to handle navigation from a [androidx.lifecycle.ViewModel]
 */
sealed class NavigationCommand {

    /**
     * Handles forward navigation
     * @param directions
     */
    data class To(val directions: NavDirections) : NavigationCommand()

    /**
     * Handles backward navigation
     */
    object Back : NavigationCommand()
}