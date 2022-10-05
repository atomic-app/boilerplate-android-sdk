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

    var streamContainer: AACStreamContainer? = null

    val countLiveData = MutableLiveData<String?>("Loading...")

    val getStreamContainerId
        get() = containerId

    fun initContainer() {
        if (streamContainer != null) {
            return
        }
        configureSdk()
        streamContainer = AACStreamContainer.Companion.create(containerId)
        streamContainer?.apply {
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
        streamContainer = AACStreamContainer(containerId)
        streamContainer?.apply {

            cardListTitle = "Demo Stream"
            cardVotingOptions = EnumSet.of(VotingOption.NotUseful, VotingOption.Useful)
            votingUsefulTitle = "Like"
            votingNotUsefulTitle = "Dislike"
            interfaceStyle = AACInterfaceStyle.AUTOMATIC
            presentationStyle = PresentationMode.WITH_ACTION_BUTTON
            cardListFooterMessage = "A Footer Message"
            cardListRefreshInterval = 30L

            cardDidRequestRunTimeVariablesHandler = { cards, done ->
                for (card in cards) {
                    val longDatePattern = "MMMM dd, yyyy 'at' HH:mm:ss"
                    val shortDatePattern = "MMM dd, yyyy"
                    val longDf: DateFormat = SimpleDateFormat(longDatePattern, Locale.getDefault())
                    val shortDf: DateFormat = SimpleDateFormat(shortDatePattern, Locale.getDefault())
                    val today = Calendar.getInstance().time
                    val formattedLongDate = longDf.format(today)
                    val formattedShortDate = shortDf.format(today)

                    card.resolveVariableWithNameAndValue("dateShort", formattedShortDate)
                    card.resolveVariableWithNameAndValue("dateLong", formattedLongDate)
                }
                done(cards)
            }
        }.also {
            streamContainer?.startUpdates(context)
        }
    }

    fun stopContainerUpdates(){
        streamContainer?.stopUpdates()
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
        val containers = arrayListOf(containerId)

        AACSDK.registerStreamContainersForNotifications(containers)

    }

    companion object {
        /** Hardcoded for boiler plate...
         * In your own app you to get these:
         * Open the Atomic Workbench, and navigate to the Configuration area.
         * Under the 'SDK' header, your API host is in the 'API Host' section,
         * your API key is in the 'API Keys' section,
         * and your environment ID is at the top of the page under 'Environment ID'. */

        const val containerId = ""
        const val apiHost = ""
        const val apiKey = ""
        const val environmentId = ""

        /* Hard coded for the purposes of this app. You would normally get via
         your authentication process */
        const val requestTokenStr = ""
    }
}

