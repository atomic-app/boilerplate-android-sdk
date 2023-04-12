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

    companion object {
        fun makeIntent(context: Context?): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }
}