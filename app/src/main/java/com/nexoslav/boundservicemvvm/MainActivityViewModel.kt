package com.nexoslav.boundservicemvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel constructor(private val bleManager: BleManager) : ViewModel(),
    BleManager.IBleConnectionListener {

    private var _connectionState: MutableLiveData<BleConnectionState> = MutableLiveData()
    val connectionState: LiveData<BleConnectionState>
        get() = _connectionState

    fun onStart() {
        bleManager.addConnectionListener(this)
    }

    fun onStop() {
        bleManager.removeConnectionListener(this)
    }

    fun onSomeUiElementClicked() {
        bleManager.doSomething()
    }

    override fun onConnectionChanged(connectionState: BleConnectionState) {
        Log.d("MainActivityViewModel", "onConnectionChanged: $connectionState")
        _connectionState.value = connectionState
    }
}