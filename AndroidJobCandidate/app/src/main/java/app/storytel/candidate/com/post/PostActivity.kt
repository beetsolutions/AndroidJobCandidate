package app.storytel.candidate.com.post

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import app.storytel.candidate.com.R
import timber.log.Timber

class PostActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
        configureNavController()

        Timber.d("----------------------->onCreate")
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun configureNavController() {
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onRestart() {
        super.onRestart()
        Timber.d("----------------------->onRestart")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("----------------------->onPause")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("----------------------->onResume")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("----------------------->onStart")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("----------------------->onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("----------------------->onDestroy")
    }
}