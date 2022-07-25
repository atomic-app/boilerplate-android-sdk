package io.atomic.android_boilerplate

import androidx.lifecycle.ViewModel
import com.atomic.actioncards.sdk.*
import java.util.*

class BoilerPlateViewModel : ViewModel() {

    var streamContainer: AACStreamContainer? = null

    init {
        configureSdk()
    }

    fun configureContainer() {
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
    }

    private fun configureSdk() {
        AACSDK.setApiHost(apiHost)
        AACSDK.setEnvironmentId(environmentId)
        AACSDK.setApiKey(apiKey)
        AACSDK.setSessionDelegate { onTokenReceived ->
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

        const val apiHost = "https://01.client-api.staging.atomic.io"
        const val apiKey = "atomic-internal:GV02lz"
        const val environmentId = "GV02lz"

        /* Hard coded for the purposes of this app. You would normally get via
         your authentication process */
        const val requestTokenStr =
            "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIwNDZjMWY0NS0xYjkwLTU1Y2MtYmQzYi03NWYyZjVkNDg2OWQiLCJpYXQiOjE2NTg3MTkyOTEsImV4cCI6MTY1ODgwNTY5MSwiaXNzIjoiYXRvbWljLWlvLWNsaWVudC1hcGkifQ.LPDHjeZboeMJJ98rKRopcVvgFNDk6iRZwViUkpxzIyCyb6lxWdeS_xE1Mg96uTokp6aYnKUffo4DaO7OiKnSObB0sE-CoelPqlGmuD4711CPo6A0g-CJVQBAuwc86IKeDaIacOpI0LORXZ-I2QeuJ0VUJBQbp1IXapki8tuoHFIxtVNr8j31pqDP_SGT--DHfMJ2x0IY3iGBcrs0gk9GryiHyq9YBp3uxWGddGi3dTDmQwxoE3qpo9fg-k40bTxC2BCkuJCN4O4kOhYa2uz9VXgQmW--CHCUBTEEKUGfLoi6X_IhdjnGwcfmPY7w41YEtqfOSnxKd6ZKY1XTA4pQ8dyCHZPF4NqUOkJZU9_N2z3NCSw24hJ8yC1HTnqsDgXxyIMPG0gjlaOMb4HMUxbxRHf9wZZQ4eKg4YqbEZjX2oasIg3j-67NKW3Rk_Muykxt2OKMGdLqwfGtEKvRIPG0KKpggJVlkkZumd5LHYelufJgjZdoqrgp6Gtl6p0fEshNd3BuWp8mumP2p7KuGCWO6LkjvsVa6qyp6EYZc5qMhp9Mu0_dIAYF-wNFU8JDdMuCEEaKpF153AKfKnADViwJ1Vv_kuqKHgfYDQnIqFEkUpHCnksM7MpWM-EOQskLux7i6WBbQKKcw5zqO2NiYhlcooxkmAR9txqykUYvEdwz3epUeJDV0lU4RI98KR9hffHmrCxEXwvMkbzz4aUl4msD8fssJmpcxUzdhTUzn3BNSHfkDd9wTNVgyKzidWyvgT2GYqZJ9vbp4Ablu-V_heAanYx9s1MFq_G0tpAzNoM2blfR4CKtnsK38X6_4n0VSVR8DWenYEvkMOEY0FuA6zRPP47xhwCeB0ISRGod62-LGQwhk3hF47I5B2YiWZUUwcM9zCxKwuj_fihKGlmgNwMgZZg5iH2FknhzFWA7klbgwsE_UCArEsZTAcDEjsH_6SSuM85xv1u7QJtKg-f7qxXt7bn0Asjzu0CaCMzlpYtYJYKeCajsEncL03gQRL6itAhBplw5Dta14CjNoloOv15sKGETIh6W67NJJNaonwO7W-YcOxdHaRMgPDqKmr3s6iQ-nbzsL0zdaPVhf1abk-xRbT7qjPTP4l8zafjeGt-Bcb3p69qROQ2if37FMD6wHHDUEfrbHMPqDEyf5GfTRtix63RcnPFZBDxdpbhb0pbLDpV3zR-VnqGTOZa1I26f3vPc8jIPPy1bl9c_5TmaB4bysdI5TtsJjlH6iDT-s6gPlZKqqFrVaCM7eVAdHjhPCzj4t4mpjfxJKSmA7D-oBLs84SeCgrOiragcHbirNCDBk-YvQK5E4SUrNeVVgsTLw-JxCvyDNpjqYt7bPcpZ95kStg"
    }
}