package com.example.moviesbymoviedb.ui.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.edit
import com.example.moviesbymoviedb.R

private const val FILE_NAME = "MY_FILE"

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        initView()
        startAnimation()
    }

    private fun initView() {
        imageView = findViewById(R.id.splash_image_view)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun startAnimation() {
        progressBar.isIndeterminate = true
        imageView.animate().setDuration(1500).alpha(1f).withEndAction {
            val isConnected = checkInternetAvailability()
            val intent = Intent(this, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putBoolean("IS_CONNECTED", isConnected)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        }

    }

    private fun checkInternetAvailability(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}