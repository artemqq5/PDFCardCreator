package com.crecardbissnes.ebissnescard.presenter.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.crecardbissnes.ebissnescard.R
import com.crecardbissnes.ebissnescard.databinding.ActivityMainBinding
import com.crecardbissnes.ebissnescard.domain.MainNavigateTransition


class MainActivity : AppCompatActivity(), MainNavigateTransition {

    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        binding.mainTransition = this

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.label) {
                "Viewer" -> {
                    binding.backButton.setImageResource(R.drawable.ic_back_white)
                    binding.backButton.visibility = View.VISIBLE
                }
                "Creating" -> {
                    binding.backButton.setImageResource(R.drawable.ic_back_red)
                    binding.backButton.visibility = View.VISIBLE
                }

                else -> {
                    binding.backButton.visibility = View.INVISIBLE
                }
            }

        }

    }

    override fun transitionFragment(nav: Int, bundle: Bundle?) {
        navController?.navigate(nav, bundle)
    }

    override fun backTransition() {
        this@MainActivity.onBackPressed()
    }



}