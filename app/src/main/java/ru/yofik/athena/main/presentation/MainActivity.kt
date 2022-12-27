package ru.yofik.athena.main.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.yofik.athena.R
import ru.yofik.athena.common.domain.model.users.User
import ru.yofik.athena.common.domain.model.users.UserV2
import ru.yofik.athena.common.presentation.customViews.avatarView.AvatarView
import ru.yofik.athena.databinding.ActivityMainBinding
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        val DESTINATIONS_WITHOUT_APP_BAR =
            listOf(
                ru.yofik.athena.login.R.id.loginFragment,
            )

        val TOP_LEVEL_DESTINATIONS =
            setOf(R.id.nav_chat_list, R.id.nav_profile, ru.yofik.athena.login.R.id.nav_login)
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
        appBarConfiguration = AppBarConfiguration(TOP_LEVEL_DESTINATIONS, binding.root)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        setRulesWhenAppBarShouldBeHide()
    }

    private fun setupDrawer() {
        binding.navigationView.setupWithNavController(navController)
    }

    private fun setRulesWhenAppBarShouldBeHide() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in DESTINATIONS_WITHOUT_APP_BAR) {
                binding.toolbar.visibility = View.GONE
                binding.navigationView.visibility = View.GONE
            } else {
                binding.toolbar.visibility = View.VISIBLE
                binding.navigationView.visibility = View.VISIBLE
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // OBSERVING VIEW EFFECTS
    ///////////////////////////////////////////////////////////////////////////

    private fun observeViewEffects() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) { viewModel.effects.collect { reactTo(it) } }
        }
    }

    private fun reactTo(effect: MainActivityViewEffect) {
        Timber.d("reactTo: $effect")
        when (effect) {
            is MainActivityViewEffect.SetStartDestination ->
                setNavGraphStartDestination(effect.destination)
            is MainActivityViewEffect.ProvideUserInfo -> setupDrawerHeader(effect.user)
        }
    }

    private fun setNavGraphStartDestination(startDestination: Int) {
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        navGraph.setStartDestination(startDestination)
        navController.graph = navGraph
    }

    private fun setupDrawerHeader(user: UserV2) {
        Timber.d("setupDrawerHeader: $user")
        val header = binding.navigationView.getHeaderView(0)
        with(header) {
            findViewById<AvatarView>(R.id.avatar_view).apply { text = user.email }
            findViewById<TextView>(R.id.name).apply { text = user.email }
            findViewById<TextView>(R.id.login).apply { text = user.login }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // EVENT WRAPPERS
    ///////////////////////////////////////////////////////////////////////////

    private fun requestDefineStartDestination() {
        viewModel.onEvent(MainActivityEvent.DefineStartDestination)
    }
}
