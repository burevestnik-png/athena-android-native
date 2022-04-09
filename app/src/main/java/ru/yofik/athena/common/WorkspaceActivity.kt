package ru.yofik.athena.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.yofik.athena.R
import ru.yofik.athena.databinding.ActivityWorkspaceBinding

class WorkspaceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkspaceBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkspaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
    }

    fun hideBottomNavigation() {
        if (binding.bottomNavigation.visibility != View.GONE) {
            binding.bottomNavigation.visibility = View.GONE
        }
    }

    fun showBottomNavigation() {
        if (binding.bottomNavigation.visibility != View.VISIBLE) {
            binding.bottomNavigation.visibility = View.VISIBLE
        }
    }

    private fun setupUI() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
    }

    companion object {
        fun intentOf(packageContext: Context): Intent {
            return Intent(packageContext, WorkspaceActivity::class.java)
        }
    }
}
