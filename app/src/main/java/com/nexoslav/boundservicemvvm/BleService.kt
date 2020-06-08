package com.nexoslav.boundservicemvvm

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import org.koin.android.ext.android.inject

class BleService : Service() {

    private val binder = LocalBinder()

    // lazy inject BusinessService into property
    private val bleManager: BleManager by inject()

    override fun onCreate() {
        super.onCreate()
        bleManager.connect()
    }

    override fun onDestroy() {
        super.onDestroy()
        bleManager.disconnect()
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    inner class LocalBinder : Binder() {
        fun getService(): BleService = this@BleService
    }
}