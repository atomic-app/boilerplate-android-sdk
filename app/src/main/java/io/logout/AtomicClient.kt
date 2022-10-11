package io.logout

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import androidx.lifecycle.LiveData
import com.atomic.actioncards.sdk.*
import kotlinx.coroutines.*

class AtomicClient constructor(private val context: Context) {
    private var atomicStreamContainerForUpdates: AACStreamContainer? = null
    private val job = Job()
    private val uiScope: CoroutineScope = CoroutineScope(Dispatchers.Main + job)

    companion object {
        private const val LOG_LEVEL = 3
        const val containerId = "4ROg0w76"
        const val apiHost = "https://67-1.client-api.atomic.io"
        const val apiKey = "atomic-public-key"
        const val environmentId = "r5WN0m"
        const val requestTokenStr =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI1MzMzNTc0IiwiZXhwIjoxNjY1NTYxNDE2LCJpYXQiOjE2NjU1MjU0MTZ9.i6PrKtQw1JesWC1_71ZpdoKY8gVHamLEVgh9ZFN_-9waHo6yy7SRYsjl9_IVQecCDaZ3Q-lIBUtITE5FIG8VqMWBzaK8Ld1EZS-89uV8M-SAJvxQ_ZnMID-606OaKoN6yGciyH3wWFW7CHeNbj03qyejjjyeMpEtZK25Gs0Mgy_dyLFqP4DZSjE1w_lWYS9YcpITE0S0eZC7Rgv5bnipKoZxNBXGEMkeW2tNc1CynKiLYWhYfyVCdyUbPkLeBPRTtDjWhfXcsgLAQ5wBNvu-ztxsWhlHc7VoNVETptdA6RzY_QcjUZ-CqlyIu6JYQcyDbIAJscF45tVTisKjR6VX1UDLsSBUEWPr04n8PI0z0BEIKxt3hivO292O4E75ALx1AsPCpDhLjtACwJJiFbdkSh535aic21JWkE1KRxMJB9xrW_XRcvEjmMfMtd2hZIu_bQPBOAQzyGaqYKLqf-WTRrcPO5zduqlSEu_2wiVmDMgHz2rs0FouPXfZlC9WsDoow2yqu_BGG-4YsEMoZ7aTfWYzZwKbR5gQoHHPwv1MPfJSL14I_CCWHIZYIuG5FAwHjz_GoNmCkUpVm9Llg30Q_l5HcJoFYc5VlmOFAmfkvTVKaxhHEo1K8aeJyyB47P9Oyh2VR2V9GENItVpazJ26t699hdnClvaMNNBTwlXGZ7E"
        private const val SERRANO_FONT = "serrano"
    }

    fun init() {
        AACSDK.enableDebugMode(LOG_LEVEL)
        AACSDK.init(context)
    }

    fun setupEnvAndUser() {

        val listOfFonts = listOf(
            AACEmbeddedFont(
                SERRANO_FONT,
                Typeface.MONOSPACE,
                AACFontWeight.None,
                AACFontStyle.None
            ),
            AACEmbeddedFont(SERRANO_FONT, Typeface.SERIF, AACFontWeight.Bold, AACFontStyle.None),
            AACEmbeddedFont(
                SERRANO_FONT,
                Typeface.SANS_SERIF,
                AACFontWeight.None,
                AACFontStyle.Italic
            ),
            AACEmbeddedFont(SERRANO_FONT, Typeface.DEFAULT, AACFontWeight.Bold, AACFontStyle.Italic)
        )
        val callback: ((String?) -> Unit) -> Unit = { onTokenReceived ->
            Log.d("Atomic Boilerplate", "onTokenReceived")
            uiScope.launch {
                delay(2000)
                onTokenReceived(requestTokenStr)
            }

        }
        AACSDK.setApiHost(apiHost)
        AACSDK.setEnvironmentId(environmentId)
        AACSDK.setApiKey(apiKey)
        AACSDK.setSessionDelegate(callback)
        AACSDK.registerStreamContainersForNotifications(listOf(containerId))
        AACSDK.registerEmbeddedFonts(listOfFonts)
    }

    fun setupDefaultStream(): AACStreamContainer {
        return AACStreamContainer(containerId)
    }

    fun registerDeviceForNotifications(token: String) {
        AACSDK.registerDeviceForNotifications(token)
    }

    fun getCardCountLiveData(): LiveData<Int?> {
        if (atomicStreamContainerForUpdates == null) {
            atomicStreamContainerForUpdates = AACStreamContainer(containerId)
            atomicStreamContainerForUpdates?.startUpdates(context)
        }
        return AACSDK.getLiveCardCountForStreamContainer(containerId)
    }

    fun reset() {
        atomicStreamContainerForUpdates?.stopUpdates()
        atomicStreamContainerForUpdates = null
        AACSDK.logout()
    }
}