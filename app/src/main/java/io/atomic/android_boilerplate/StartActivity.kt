package io.atomic.android_boilerplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import io.atomic.android_boilerplate.databinding.ActivityHomeBinding

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            // login and go to main activity
            launchHomeActivity()
        }
    }


    private fun launchHomeActivity() {
        val intent = HomeActivity.makeIntent(context = this)
        startActivity(intent)
    }
}