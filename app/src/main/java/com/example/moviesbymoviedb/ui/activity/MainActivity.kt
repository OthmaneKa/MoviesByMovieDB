package com.example.moviesbymoviedb.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.moviesbymoviedb.R
import com.example.moviesbymoviedb.data.LocalDb
import com.example.moviesbymoviedb.ui.fragment.MoviesListFragment
import com.facebook.stetho.Stetho
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController
    private var isConnected = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bundle = intent.extras
        isConnected = bundle?.getBoolean("IS_CONNECTED")!!
        bundle.putBoolean("IS_CONNECTED", isConnected)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph, bundle)
        setupNavigation()
        setUpListener()
        LocalDb.getInstance(this)

    }

    private fun setupNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_nav)
        setupWithNavController(bottomNavigationView, navController)
    }

    private fun setUpListener() {
        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            if (nd.id == R.id.moviesListFragment || nd.id == R.id.favoriteListFragment) {
                bottomNavigationView.visibility = View.VISIBLE
            } else {
                bottomNavigationView.visibility = View.GONE

            }
        }
    }

}