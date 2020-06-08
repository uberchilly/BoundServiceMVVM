package com.nexoslav.boundservicemvvm

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * - we can add more activities like this
 * - we can also extract BaseServiceActivity to do bind/unbind in one place for multiple activities
 * - as long as one activity in bound to the service it will stay alive, and will not trigger BleManager disconnect
 * - data from service (like connection state) flows through view model, there it can be prepared a bit better for displaying
 * - we can interact with ble manager from UI -> ViewModel -> BleManager with this solution;
 */
class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModel()

    private lateinit var mService: BleService
    private var mBound: Boolean = false

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as BleService.LocalBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.connectionState.observe(this, Observer {
            connectionStateLabel.text = it.toString()
        })

        connectionStateLabel.setOnClickListener {
            viewModel.onSomeUiElementClicked() //for demo only, not real implementation
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
        // Bind to LocalService
        Intent(this, BleService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        if (!isChangingConfigurations) {
            unbindService(connection)
            mBound = false
        }
        viewModel.onStop()
    }
}