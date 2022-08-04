package io.atomic.android_boilerplate

import android.util.Log
import androidx.lifecycle.ViewModel
import com.atomic.actioncards.sdk.*
import java.util.*

class BoilerPlateViewModel : ViewModel() {

    var streamContainer: AACStreamContainer? = null

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

        /* Hard coded for the purposes of this app. You would normally get via
         your authentication process */
        const val requestTokenStr =
            "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJjNjNkZjJmMC0zZmVmLTU5NTMtOGQ2Mi1iNTg1MGFlYjljOWMiLCJpYXQiOjE2NTk1NzI2NTYsImV4cCI6MTY1OTY1OTA1NiwiaXNzIjoiYXRvbWljLWlvLWNsaWVudC1hcGkifQ.mMtNKEqAK9yXs4mSrVwAZ7ykBzpXVzUPgr3hKzm6QiBjJUbkPMu3MFBDq3qLN5KM8z_5A_H0qjICJY3Z9pj8wCcRor_Yw4lGkPLnL6xay7suSIx2Ddy-Ak6x9aslFpi67oAwgK5Fz0kawypvkjidzT5lXnY1vGKk6sIEQxkc8X4YCiqnkySxT-EuBtBf8TOS5MkGtfvP8IOI4vea0xOefbmTR-I137I1jouj2g3ScB42-_eiAky7fiz_QIL9lwD9QsGeeYKzp4wgm8nRPsOESjQ-dXQ1VQRy5iMqi4pcHjOeAPoVIJ_5Z5Q63CJtdZbSHps6aObKMA8EUg58QW4sQTq4GhvOkP0zxFdKA41ileE3kDjvREAyN4id1Ox9foYv-rum7UYz8Hlr5VJ3N3O9F84dwS1hMQIZiNGWLWLbP7lfi1We--BYtJIihvJcwy81oSkLCASMJVmyLQcm1CaaLnp75MWiNmGKyLs6bISZ7CcdoIHsCbA0kXyaf5nZPQW5uYSRJJ0AbDwvlTiRhqx8R9NrhCGR3UyF-4zJ1QC-FvJvLNB0rJQ3oIOrEyaDZxS7xuynzj6zks1zz4ZbhC_ISA6qReGgz5dT8KeUofOJ_wFEQB1FsILzTQnKJNk1VVMWsuPHVxOoCwZ9spe6GC_wwoQpDaVWUV5h7TedeaWYfpJYwzpmTY_hTLVMdo0VvipRxNc6VENC_aUlWnJ7mcfsN6rPWwEYFliGaFRFd7feOLCBFjI-Skv7HWKLuw68PEZ-spiqA1TXDYOSr6wWNN7U0TbyzmdZ1y2RJ-9LkZCXHwkv3RmuGXL0ahbh48_uULhVNv83yvQ8UHUa0pNVFRUFclnmocEuvp_r0LsjSeslLc7IVYRi1izuQCnPITCh3Pegy_jhaWNCckN-IfUh5bXO8WahTXTMVWQj11PbfpG7oB0V7rDiRwTG2HL14vAAbTck6NKH78mOO9vkmom9kRPaAy0e87Zmxr3v6CiFM1W9uhYQ-HxKtn2U9u2gjvYu9YKsr00fkueQCM-DSbIqGh7xpghJwH-xDL6Ru1Vlnz1jC15Zj0zPImsTVw27glVyXATYktfvm24TwihKHK3Yd6BhEe5lLi0qH1xJ_IcGCOh5hgfxImTjm6kTdnq11DNXcInBi0ylsw_TSj7yRgSNzt-_fKOBoJPQ1xkzbaikfjeeOtmjZAvUyYeyZXGvebxx_hEX_eQGOmgDx2j5toX2pkda0MO-IXhN_En7YChl5EegJDOwFE3ra_v1UMKutdhq8wL8zmln0LWQUh8tJ0V0AoWev9K-zNlYWm7qd_oXXB7ZAT1TKwlG0KuGcknRdV90q5qw5hS0hHOSVTQxSYon1IEp1w"
    }
}

