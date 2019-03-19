package com.me.bui.wifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.util.Log
import android.net.wifi.WifiInfo
import android.net.NetworkInfo




/**
 * Created by mao.bui on 3/18/2019.
 */

class WifiReceiver : BroadcastReceiver() {
    companion object {
        val TAG = WifiReceiver.javaClass.simpleName
    }
    override fun onReceive(context: Context, intent: Intent) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo

            if (networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_WIFI &&
                networkInfo.isConnected
            ) {
                // Wifi is connected
                val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val wifiInfo = wifiManager.connectionInfo
                val ssid = wifiInfo.ssid

                Log.e(TAG, " -- Wifi connected ---  SSID $ssid")

            }
        } else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION, ignoreCase = true)) {
            val wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)
            if (wifiState == WifiManager.WIFI_STATE_DISABLED) {
                Log.e(TAG, " ----- Wifi  Disconnected ----- ")
            }

        }
    }

}