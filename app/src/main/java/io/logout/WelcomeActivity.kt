package io.logout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.atomic.android_boilerplate.R
import io.atomic.android_boilerplate.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var atomicClient: AtomicClient
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)

        atomicClient = AtomicClient(applicationContext)

        binding.loginButton.setOnClickListener {
            atomicClient.init()
            atomicClient.setupEnvAndUser()

            val intent = Intent(applicationContext, CountActivity::class.java)
            startActivity(intent)
        }
    }
}