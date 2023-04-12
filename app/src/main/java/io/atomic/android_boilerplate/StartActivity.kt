package io.atomic.android_boilerplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import io.atomic.android_boilerplate.databinding.ActivityHomeBinding

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val viewModel: BoilerPlateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            // login and go to main activity
            launchHomeActivity()
        }

        // If called from here it doesn't work as it's not setup correctly
        //viewModel.configureSdk()
        //viewModel.startContainerUpdates(this)
    }


    private fun launchHomeActivity() {
        AtomicClass.startSession()

        val intent = HomeActivity.makeIntent(context = this)
        startActivity(intent)
    }
}