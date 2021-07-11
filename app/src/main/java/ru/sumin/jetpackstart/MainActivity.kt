package ru.sumin.jetpackstart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.sumin.jetpackstart.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            launchFirstScreen()
        }
    }

    private fun launchFirstScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFragment.newInstance(Level.EASY))
            .commit()
    }
}
