package io.atomic.android_boilerplate

import android.util.Log
import androidx.lifecycle.ViewModel
import com.atomic.actioncards.sdk.*
import java.util.*

class BoilerPlateViewModel : ViewModel() {

    var streamContainer: AACStreamContainer? = null

    var secondaryContainer: AACStreamContainer? = null

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

    fun initSecondaryContainer() {
        if (secondaryContainer != null) return

        secondaryContainer = AACStreamContainer.Companion.create(secondContainerId)
        secondaryContainer?.apply {
            cardListTitle = "Notifications"
            cardListFooterMessage = null
        }
    }

    private fun configureSdk() {
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

        const val containerId = "3"
        const val apiHost = "https://02.client-api.staging.atomic.io"
        const val apiKey = "atomic-internal:5LwArq"
        const val environmentId = "5LwArq"

        const val secondContainerId = "gnxEE2xM"

        /* Hard coded for the purposes of this app. You would normally get via
         your authentication process */
        const val requestTokenStr = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJjNjNkZjJmMC0zZmVmLTU5NTMtOGQ2Mi1iNTg1MGFlYjljOWMiLCJpYXQiOjE2NjgwMzUxMDcsImV4cCI6MTY2ODEyMTUwNywiaXNzIjoiYXRvbWljLWlvLWNsaWVudC1hcGkifQ.JalEe0YsbNGq3BUKziTVx7sQXSc9RkJk5stxmpocoYc660oKO0_MfcO23PLx3wqChHrjPNXd3V7sX21qjr4rmCRPiFtxsb3jASp0dzK09kLqS9VP4eJGDEm-7qibnEGo-4E3TcBYdvHw76PpJaKcA8XL_KuDXj-YCY2GnRSq9qilgLf0ntSbagLLgmeP70Kni6u2nDEVnxkUyZMbCMMnj2nI_gHo4jNkhdB73XgBTEfddcxRR-Y_Il-2H4prpv6gtPzW9XzNoMp3FWTQfd8Y_PMdufJAW-YGnqfNRDA9vWb35l-_xp20LFCjrluM7fE6yLs9bBZjfVj55DDkNwspEEgY5Zi0nzqfTSYUkoxgJuh5Mwe8qWMXPVQNFU2gea5xr34RX9zvcRPIgvl_X25TdiqjisSOPiY1AMr9QsK0ABRD9eFJDzrk_zN2YysU08ad0AYTtT9WZr13nKHX5GBcqo421_hY_Elapni4uNx9U1UW47hhyR9BnJ4kQNpnofhwChqoW2ecYF-Ym8tcKi2QKLpK66iXM0NTBhKPCGnP_wQ0ffYuQOTcDo3QXuVlOOrEDdZ7So0g_948KErxY__r9GvaN6dp8HhTsi-FvcUYiKPBJ88h9AOb5B8cYPmaLJni53_MSWRsY6z73KytkhkEY7ekhzyvDyZpKz9yCh6wZzF3ycJLMU5mZuIRHTW5_xVbgZP3TV6ZDd14u0TXrezAd6qOueql4_23Pip0Jo8QtFB-5EUMpzUpWI7UkdoOv4_Q5p9zXxF09AMVduItH-HH8jIPTqBrQNCxbT9Ik29wRArS8TcbChb2MXojEiwYIcQaB0nrPNQNrwsXTReJSMbxhmXimWrKWX7cqAbIsWjhVfY7lw4jR-YPDjfo06x0CBdNYhimH6BffBdTbOqscyynSZ0d5VmVV4kHJTvuj-xRaKrAcqGZ54pyX_Vj7nUXudm27Ew0sWDErUDSrN44z99yBwCOhaZNZlNeprMXMfBHKV7NJq_XWZdjCkwhnG23DLE2iQVs-I4HQwcGfPD90QTZvp-KTl-xxSJHmzSFvLSo7yJvF3Pz0OJdRmG5VAY7y2lcm802Pk6aKI9el6z7fBV-v5u7F5I__CvhWcBb8DQaMrWS8IDdk0h_7jO6zVBD7F3IG1IhbWrT3nmBPgaagoBZZQ6wwthsVgoC3-kcD0eJCZcLgUecQ74lUGHlM3X6cVbeKbdmooClnlMMBXf2yyNY9Pqb-aCgImcqR1F-YQeIbOqxoF-GoJGqw1UBT4HmnqU7wjI9-x1_uyeAHAP57kS_TQlewIHqF-n2s7ln6flktreWuyTahIrI26BcL3kAScUanR57BLWGDqC8LMPl01joKg"
    }
}

