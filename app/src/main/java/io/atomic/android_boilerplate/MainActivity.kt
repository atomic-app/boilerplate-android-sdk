package io.atomic.android_boilerplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BoilerPlateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[BoilerPlateViewModel::class.java]
        setContentView(R.layout.activity_main)

        viewModel.initContainer()
        viewModel.streamContainer?.start(R.id.cardsContainer, supportFragmentManager)
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.streamContainer?.destroy(supportFragmentManager)
    }
}