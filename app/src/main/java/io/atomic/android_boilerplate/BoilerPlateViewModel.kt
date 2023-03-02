package io.atomic.android_boilerplate

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atomic.actioncards.sdk.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class BoilerPlateViewModel : ViewModel() {

    fun configureSdk() {
        AACSDK.setApiHost(apiHost)
        AACSDK.setEnvironmentId(environmentId)
        AACSDK.setApiKey(apiKey)
        AACSDK.setSessionDelegate { onTokenReceived ->
            Log.d("Atomic Boilerplate", "onTokenReceived")
            onTokenReceived(requestTokenStr)
        }
    }

    companion object {
        /** Hardcoded for boiler plate...
         * In your own app you to get these:
         * Open the Atomic Workbench, and navigate to the Configuration area.
         * Under the 'SDK' header, your API host is in the 'API Host' section,
         * your API key is in the 'API Keys' section,
         * and your environment ID is at the top of the page under 'Environment ID'. */

        // Id for a stream container that shows a selected amount of cards
        const val dashboardID = ""
        // ID for a stream container that shows all cards
        const val allCardsId = ""
        const val apiHost = ""
        const val apiKey = ""
        const val environmentId = ""

        /* Hard coded for the purposes of this app. You would normally get via
         your authentication process */
        const val requestTokenStr = ""
    }
}

