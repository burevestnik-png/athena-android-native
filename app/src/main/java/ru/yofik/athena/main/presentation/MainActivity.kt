package ru.yofik.athena.main.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.R
import ru.yofik.athena.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        val DESTINATIONS_WITHOUT_APP_BAR =
            listOf(
                ru.yofik.athena.login.R.id.loginFragment,
            )
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val viewModel by viewModels<MainActivityViewModel>()

    ///////////////////////////////////////////////////////////////////////////
    // LIFECYCLE
    ///////////////////////////////////////////////////////////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavController()
        setupAppBarConfiguration()
        setupActionBar()
        setupDrawer()

        observeViewEffects()
        requestDefineStartDestination()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment

        navController = navHostFragment.navController
    }

    private fun setupAppBarConfiguration() {
        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.nav_chat_list, R.id.nav_profile), binding.root)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.layoutAppBar.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        setRulesWhenAppBarShouldBeHide()
    }

    private fun setupDrawer() {
        binding.navigationView.setupWithNavController(navController)
    }

    private fun setRulesWhenAppBarShouldBeHide() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in DESTINATIONS_WITHOUT_APP_BAR) {
                binding.navigationView.visibility = View.GONE
            } else {
                binding.navigationView.visibility = View.VISIBLE
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // OBSERVING VIEW EFFECTS
    ///////////////////////////////////////////////////////////////////////////

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

    ///////////////////////////////////////////////////////////////////////////
    // EVENT WRAPPERS
    ///////////////////////////////////////////////////////////////////////////

    private fun requestDefineStartDestination() {
        viewModel.onEvent(MainActivityEvent.DefineStartDestination)
    }
}
