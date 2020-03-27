package app.storytel.candidate.com.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import app.storytel.candidate.com.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_scrolling_new.*

class ScrollingActivityNew : DaggerAppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling_new)

        if (savedInstanceState != null) return

        if (isConnected(this)) {
            fragmentContainer.removeAllViews()
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, ScrollingPostFragment())
                    .addToBackStack(null)
                    .commit()
        }else {
            Toast.makeText(this, "Network is not available", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    private fun isConnected(context: Context): Boolean {
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected ?: false
    }
}