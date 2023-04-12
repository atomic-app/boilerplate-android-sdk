package io.atomic.android_boilerplate

import android.app.Application


/** Entry point to our application. The SDK needs to do some init code [onCreate] */
class BoilerPlateApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        AtomicClass.init(this)
    }

}