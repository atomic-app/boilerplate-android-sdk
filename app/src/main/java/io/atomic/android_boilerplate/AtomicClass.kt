package io.atomic.android_boilerplate

import android.app.Application
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import com.atomic.actioncards.sdk.*

object AtomicClass {

    private val appVersionFieldName = "last_seen_app_version"
    private var hasUpdatedUser = false

    private var isInitialised = false // Used to make sure Atomic is initialised before attempting to call anything else
    private var hasSession = false // Used to check if a user is logged into atomic

    private lateinit var application: Application

    // We hold both instances of the card views in here as we found this gives us
    // better and more predictable results.
    private var singleCard: AACSingleCardView? = null
    private var messageCentre: AACStreamContainer? = null

    private val TAG = this::class.java.simpleName

    fun messageCentreID(): String = BoilerPlateViewModel.containerId
    fun dashboardID(): String = BoilerPlateViewModel.singleCardID

    fun init(application: Application) {
        AtomicClass.application = application
        AACSDK.init(application)
        AACSDK.enableDebugMode(2)
        isInitialised = true
    }


    fun startSession() {
        if (!isInitialised) { // Stop Atomic from trying to run more than once
            Log.i(TAG, "Unable to start Atomic session.")
            return
        }

        AACSDK.setApiHost(BoilerPlateViewModel.apiHost)
        AACSDK.setEnvironmentId(BoilerPlateViewModel.environmentId)
        AACSDK.setApiKey(BoilerPlateViewModel.apiKey)

        AACSDK.setSessionDelegate(requestToken)
        hasSession = true
    }

    // Set Session Delegate
    private val requestToken: (((String?) -> Unit) -> Unit) = { onTokenReceived -> onTokenReceived(BoilerPlateViewModel.requestTokenStr)}

    fun getLiveCardCount(streamId: String): LiveData<Int?> {
        return AACSDK.getLiveCardCountForStreamContainer(streamId)
    }

    fun endSession(callback: ((AACSDKLogoutResult) -> Unit)?) {
        if (!isInitialised) return

        hasUpdatedUser = false

        // Referencing AACSDK in tests causes failure, so added this check so ensure it has been configured first
        if (hasSession) {
            hasSession = false
            AACSDK.logout(callback)
        } else {
            callback?.invoke(AACSDKLogoutResult.Success())
        }
    }

    fun onCreate() {
        Log.i(TAG, "On Create")
        messageCentre = AACStreamContainer(messageCentreID())
        singleCard = AACSingleCardView(dashboardID())
    }

    fun onPause(childFragmentManager: FragmentManager) {
        Log.i(TAG, "On Pause")
        messageCentre?.stopUpdates()
        messageCentre?.destroy(childFragmentManager)
        singleCard?.destroy(childFragmentManager)
    }

    fun getDashboard(): AACSingleCardView? = singleCard

    fun getMessageCenter(): AACStreamContainer? = messageCentre
}