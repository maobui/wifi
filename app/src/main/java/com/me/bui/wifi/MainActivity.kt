package com.me.bui.wifi

import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log


class MainActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var mWifiConnectionReceiver: WifiConnectionReceiver

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mWifiConnectionReceiver = WifiConnectionReceiver()
        registerReceiver(mWifiConnectionReceiver, IntentFilter(WifiConnectionReceiver.getIntentFilterForWifiConnectionReceiver()))
    }

    override fun onResume() {
        super.onResume()
        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this)
    }

    private fun checkConnection() {
        val isConnected = ConnectivityReceiver.isConnected()
        showSnack(isConnected)
    }

    // Showing the status in Snackbar
    private fun showSnack(isConnected: Boolean) {
        val message: String
        val color: Int
        if (isConnected) {
            message = "Good! Connected to Internet"
            color = Color.WHITE
        } else {
            message = "Sorry! Not connected to internet"
            color = Color.RED
        }

//        val snackbar = Snackbar
//            .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG)
//
//        val sbView = snackbar.getView()
//        val textView = sbView.findViewById(android.support.design.R.id.snackbar_text) as TextView
//        textView.setTextColor(color)
//        snackbar.show()
        Log.e(TAG, message)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showSnack(isConnected)
    }


    override fun onDestroy() {
        unregisterReceiver(mWifiConnectionReceiver)
        super.onDestroy()
    }
}
