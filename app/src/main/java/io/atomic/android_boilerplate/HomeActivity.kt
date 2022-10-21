package io.atomic.android_boilerplate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit


class HomeActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: BoilerPlateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<HomeFragment>(R.id.fragment_container)
            }
        }

        /********
         * Must be called here once the token is established?
         */

        // configure the sdk with hardcoded creds.
        viewModel.configureSdk()
        // start the websocket container updates to notify of incoming cards
        // via LiveData observer
        viewModel.startContainerUpdates(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        //viewModel.stopContainerUpdates()
        viewModel.allCardsContainer?.destroy(supportFragmentManager)
    }

    companion object {
        fun makeIntent(context: Context?): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }
}