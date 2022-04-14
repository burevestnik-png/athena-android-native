package ru.yofik.athena.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.yofik.athena.R
import ru.yofik.athena.databinding.ActivityMainBinding
import ru.yofik.athena.login.presentation.LoginFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.rootLayout, LoginFragment.newInstance())
            .commit()
    }
}