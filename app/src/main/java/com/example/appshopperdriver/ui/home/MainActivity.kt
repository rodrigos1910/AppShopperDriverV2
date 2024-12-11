package com.example.appshopperdriver.ui.home

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.appshopperdriver.R
import com.example.appshopperdriver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {



    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    private val TIME_INTERVAL = 500 // Intervalo de tempo em milissegundos
    private var lastClickTime: Long = 0 // Tempo do último clique

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        // Alterar a cor da barra de status
        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryColor)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.secondaryColor)

        navController = navHostFragment.navController

        binding.bottomNavMain.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.rideFragment,
                R.id.ordersFragment,
                R.id.moreFragment
            )
        )

        setSupportActionBar(binding.toolbarApp)
        setupActionBarWithNavController(navController,appBarConfiguration)
        binding.toolbarApp.setupWithNavController(navController, appBarConfiguration)


        navListener()
    }



    private fun navListener(){
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isTopLevelDestination =
                appBarConfiguration.topLevelDestinations.contains(destination.id)
            if (!isTopLevelDestination) {
                binding.toolbarApp.navigationIcon
                navigateToRoot()
            }
            else{

                lastClickTime = 0
            }
        }

    }


    private fun navigateToRoot() {

        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime < TIME_INTERVAL) {

            // Double click
            navController.navigateUp()
            lastClickTime = 0 // Reset last click time
        } else {
            // Single click
            lastClickTime = currentTime
        }
    }


}