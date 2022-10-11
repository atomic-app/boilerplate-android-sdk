package io.logout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.atomic.android_boilerplate.R
import io.atomic.android_boilerplate.databinding.ActivityCountBinding

class CountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountBinding
    private lateinit var atomicClient: AtomicClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_count)
        atomicClient = AtomicClient(applicationContext)
        val cardCountLiveData = atomicClient.getCardCountLiveData()
        cardCountLiveData.observe(this) {
            binding.count.text = "Number of cards: $it"
        }

        binding.logout.setOnClickListener {
            atomicClient.reset()
            finish()
        }
    }
}