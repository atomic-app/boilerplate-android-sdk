package io.atomic.android_boilerplate

import android.app.Application
import android.content.pm.PackageManager
import android.os.Bundle
import com.atomic.actioncards.sdk.AACSDK


/** Entry point to our application. The SDK needs to do some init code [onCreate] */
class BoilerPlateApplication : Application() {

    private val data: Bundle = packageManager.getApplicationInfo(
        packageName,
        PackageManager.GET_META_DATA
    ).metaData

    override fun onCreate() {
        super.onCreate()

        AACSDK.init(this)

        configureSdk()
    }

    private fun configureSdk() {
        data.getString("atomic.apiHost")?.let { AACSDK.setApiHost(it) }
        data.getString("atomic.environmentId")?.let { AACSDK.setEnvironmentId(it) }
        data.getString("atomic.apiKey")?.let { AACSDK.setApiKey(it) }
        AACSDK.setSessionDelegate(requestToken)
    }

    private val requestToken: (((String?) -> Unit) -> Unit) = { onTokenReceived ->
        onTokenReceived(data.getString("atomic.requestToken"))
    }

}