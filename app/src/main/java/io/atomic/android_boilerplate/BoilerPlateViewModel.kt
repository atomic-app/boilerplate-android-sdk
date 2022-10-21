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

    var dashboardContainer: AACStreamContainer? = null
    val countLiveData = MutableLiveData<String?>("Loading...")
    val getDashboardId get() = dashboardID

    var allCardsContainer: AACStreamContainer? = null
    val countLiveDataAllCards = MutableLiveData<String?>("Loading...")
    val getAllCardsId get() = allCardsId

    fun initContainer() {
        if (dashboardContainer != null) {
            return
        }
        configureSdk()
        dashboardContainer = AACSingleCardView.Companion.create(dashboardID)
        dashboardContainer?.apply {
            cardListTitle = "Demo Stream"
            cardVotingOptions = EnumSet.of(VotingOption.NotUseful, VotingOption.Useful)
            votingUsefulTitle = "Like"
            votingNotUsefulTitle = "Dislike"
            interfaceStyle = AACInterfaceStyle.AUTOMATIC
            presentationStyle = PresentationMode.WITH_ACTION_BUTTON
            cardListFooterMessage = "A Footer Message"
            cardListRefreshInterval = 30L
        }
        registerContainersForNotifications()
    }

    fun startContainerUpdates(context: Context) {
        dashboardContainer = AACStreamContainer(dashboardID)
        dashboardContainer?.apply {
            dashboardContainer?.startUpdates(context)
        }

        allCardsContainer = AACStreamContainer(allCardsId)
        allCardsContainer?.apply {
            allCardsContainer?.startUpdates(context)
        }
    }

    fun stopContainerUpdates(){
        dashboardContainer?.stopUpdates()
        dashboardContainer?.stopUpdates()
    }

    fun configureSdk() {
        AACSDK.setApiHost(apiHost)
        AACSDK.setEnvironmentId(environmentId)
        AACSDK.setApiKey(apiKey)
        AACSDK.setSessionDelegate { onTokenReceived ->
            Log.d("Atomic Boilerplate", "onTokenReceived")
            onTokenReceived(requestTokenStr)
        }
    }

    /** Register any containers we want to receive notifications for. Also look in
     * BoilerplateFirebaseMessaging for how to intercept messages and send notifs */
    private fun registerContainersForNotifications() {
//        val containers = arrayListOf(containerId)
//
//        AACSDK.registerStreamContainersForNotifications(containers)
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

