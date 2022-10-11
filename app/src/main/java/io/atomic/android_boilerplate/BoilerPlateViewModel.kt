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

        const val containerId = "3"
        const val apiHost = "https://02.client-api.staging.atomic.io"
        const val apiKey = "atomic-internal:5LwArq"
        const val environmentId = "5LwArq"

        /* Hard coded for the purposes of this app. You would normally get via
         your authentication process */
        const val requestTokenStr =
            "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJjNjNkZjJmMC0zZmVmLTU5NTMtOGQ2Mi1iNTg1MGFlYjljOWMiLCJpYXQiOjE2NjUwMjU3NjAsImV4cCI6MTY2NTExMjE2MCwiaXNzIjoiYXRvbWljLWlvLWNsaWVudC1hcGkifQ.IMGn74gyGTFcezMrj_XFcUW9IDtJBZHse51qyYSIX0oShxFCXlF3z55u1eCyZH7m9yIFXQg61dZy97ACHJzXNnssQeULg8xMRGqzI6dMRSyYfSmQJiZThAk7PddNg3d_P3C2FqDFBeYZGWRx-_h7T3XRWPofPzLL5BzKQvGGYguTssyJ3AOeBBku6MesM_4D5xJ2HqfWZMwedGRXZ7NbueLRePdbMPVOh_Rmwj3Vknly2l6hQEUOEQKAmKosUrFqhbXA00BlrNhA0G25t2thJ38icIPhfV1RNBoGUKS1uv68s9BRFCGmAUtoW1RW7kWXP7rWG_ZkFtl7Netp-MDuDd1yEgVsfukHDutdknkhlGg-4Cw-At562IzREakmeX7ybYpN6C8zgDoN06TQD5mUr-VhFxeNkLisSu3PEy1Kw1PQXRJnNIxreFn1WFy_5ojmfaIGxoiyD1V5TV5uTgTM2zQ5g1A9HqsntmaxyZ8GJ_Vc2y0X-zJNYF3exp5byWhpEHu07_129c5oxJ9kTltaoJWh5E7nmolwvd61WTvLVO9BrXoQ7nF86Vr-B6Wb_jPBGGfuFlGtYx1pzSj99leggxCXdxMzDui5nOQrtcalQ8wfr8ftOM2eIINgnSytQUW-EH4XZGeqmKn3FQp_GjY4ntzgVdSajIC8grPIGxw1d6V3qynh3IhREu9c3HLumf3XwMgUZOLFQejUZd5V0iTv9eSAQwGHa50CsHgd6NlrzUOc6p0_pWZ98rh6pJ3mar_qw4UzddZTyg__OAkk_5lvN5RgNXpJbqZ2v57cGF_ye_p5VxWa8F0rlo5kDTIoQv8yXjjwLXYQKQhJs0e6YsGQ6SJp-81FZ-EjVzNwSSKxqnWbdumtJxDD86o0YWbAOpG9Sf38yY40A_OIJoGyo5mDhwG4NvKSECn86ryeuyix1MSqmn9nUi4IR-cCwceInGzfRhTlDoevswDDZI8mHoUIC0eMW5YLnlSBTOM08Ak7zBNSh_izWET-SiC1sRbGaxiNTB8Llfplmi5WestftppcpQYSpHNfgR4Hl8MVWctIlEgUJ38nR2yxPaV-_nZF8FZWXDjA1WfiBF1T8bR5JlR3t7me98jDHhaXrsMo2IQVHGBtM1M26WxnHzZv87PsVFAiVQQkN0FmTtQE6pZU0nD2l5aWX7OOPMmFG3c1DN9fyREoIickNz0ZMdGVUzC8v1Ok9k5tB0bLr7ex0LbRmoFD1OfdPt-Mjpf9QsgbvJO1XAW-1KzrAp3l_z-sz8f6q97zFHJL2ZUKUT3pcjB8K_rs14SgqpKjYxXbIsPKes-ANmevKSC-8QTJtRf9zZaal1UVYhLPnd6hyNWPRhYQzbNlmg"
    }

//    companion object {
//        /** Hardcoded for boiler plate...
//         * In your own app you to get these:
//         * Open the Atomic Workbench, and navigate to the Configuration area.
//         * Under the 'SDK' header, your API host is in the 'API Host' section,
//         * your API key is in the 'API Keys' section,
//         * and your environment ID is at the top of the page under 'Environment ID'. */
//
//        const val containerId = ""
//        const val apiHost = ""
//        const val apiKey = ""
//        const val environmentId = ""
//
//        /* Hard coded for the purposes of this app. You would normally get via
//         your authentication process */
//        const val requestTokenStr = ""
//    }
}

