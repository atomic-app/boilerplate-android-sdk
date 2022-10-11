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
        const val containerId = ""
        const val apiHost = ""
        const val apiKey = ""
        const val environmentId = ""
        const val requestTokenStr = ""
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