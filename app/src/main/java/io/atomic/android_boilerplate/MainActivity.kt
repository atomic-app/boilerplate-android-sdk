package io.atomic.android_boilerplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.atomic.actioncards.sdk.AACStreamContainer

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BoilerPlateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[BoilerPlateViewModel::class.java]
        setContentView(R.layout.activity_main)

        if (viewModel.streamContainer == null) {
            viewModel.streamContainer = AACStreamContainer.Companion.create("QgYOWRYq")
            // apply any additional configuration / styling to your container
            viewModel.configureContainer()

        }

        viewModel.streamContainer?.start(R.id.cardsContainer, supportFragmentManager)
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.streamContainer?.destroy(supportFragmentManager)
    }
}