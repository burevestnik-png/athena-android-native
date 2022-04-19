package ru.yofik.athena.main.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.R
import ru.yofik.athena.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavController()
        setupBottomNavigation()
        observeViewEffects()

        requestDefineStartDestination()
    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment

        navController = navHostFragment.navController
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setupWithNavController(navController)
        setRulesWhenBottomNavigationShouldBeHide()
    }

    private fun setRulesWhenBottomNavigationShouldBeHide() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in DESTINATIONS_WITHOUT_BOTTOM_NAV) {
                binding.bottomNavigation.visibility = View.GONE
            } else {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }
    }

    private fun observeViewEffects() {
        viewModel.effects.observe(this) { reactTo(it) }
    }

    private fun reactTo(effect: MainActivityViewEffect) {
        when (effect) {
            is MainActivityViewEffect.SetStartDestination ->
                setNavGraphStartDestination(effect.destination)
        }
    }

    private fun setNavGraphStartDestination(startDestination: Int) {
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        navGraph.setStartDestination(startDestination)
        navController.graph = navGraph
    }

    private fun requestDefineStartDestination() {
        viewModel.onEvent(MainActivityEvent.DefineStartDestination)
    }

    companion object {
        val DESTINATIONS_WITHOUT_BOTTOM_NAV =
            listOf(
                ru.yofik.athena.login.R.id.loginFragment,
                ru.yofik.athena.chat.R.id.chatFragment,
                ru.yofik.athena.createchat.R.id.createChatFragment
            )
    }
}
