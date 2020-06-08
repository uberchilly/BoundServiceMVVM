package com.nexoslav.boundservicemvvm

import android.util.Log

class BleManager {
    interface IBleConnectionListener {
        fun onConnectionChanged(connectionState: BleConnectionState)
    }

    private val connectionListenerListeners: HashSet<IBleConnectionListener> = HashSet()


    fun addConnectionListener(listener: IBleConnectionListener) {
        connectionListenerListeners.add(listener)
    }

    fun removeConnectionListener(listener: IBleConnectionListener) {
        if (connectionListenerListeners.contains(listener)) {
            connectionListenerListeners.remove(listener)
        }
    }

    private fun publishConnectionState(connectionState: BleConnectionState) {
        for (listener in connectionListenerListeners) {
            listener.onConnectionChanged(connectionState)
        }
    }

    fun connect() {
        //will simulate connect
        publishConnectionState(BleConnectionState.CONNECTING)
        publishConnectionState(BleConnectionState.CONNECTED)
    }

    fun disconnect() {
        //will simulate disconnect
        publishConnectionState(BleConnectionState.DISCONNECTING)
        publishConnectionState(BleConnectionState.DISCONNECTED)
    }

    fun doSomething() {
        Log.d("BleManager", "doSomething() called")
    }

}

sealed class BleConnectionState {
    object CONNECTED : BleConnectionState()
    object CONNECTING : BleConnectionState()
    object DISCONNECTING : BleConnectionState()
    object DISCONNECTED : BleConnectionState()
}