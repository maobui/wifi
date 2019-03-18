package com.me.bui.wifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.text.TextUtils
import android.util.Log


/**
 * Created by mao.bui on 3/18/2019.
 */

class WifiConnectionReceiver : BroadcastReceiver() {

    companion object {
        val TAG = "WifiConnectionReceiver"
        /**
         * Notifies the receiver to turn wifi on
         */
        val ACTION_WIFI_ON = "android.intent.action.WIFI_ON"

        /**
         * Notifies the receiver to turn wifi off
         */
        val ACTION_WIFI_OFF = "android.intent.action.WIFI_OFF"

        /**
         * Notifies the receiver to connect to a specified wifi
         */
        val ACTION_CONNECT_TO_WIFI = "android.intent.action.CONNECT_TO_WIFI"

        fun getIntentFilterForWifiConnectionReceiver(): IntentFilter {
            val randomIntentFilter = IntentFilter(ACTION_WIFI_ON)
            randomIntentFilter.addAction(ACTION_WIFI_OFF)
            randomIntentFilter.addAction(ACTION_CONNECT_TO_WIFI)
            return randomIntentFilter
        }
    }

    private lateinit var wifiManager: WifiManager

    override fun onReceive(c: Context, intent: Intent) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Log.w(TAG, "onReceive() called with: intent = [$intent]")

        wifiManager = c.getSystemService(Context.WIFI_SERVICE) as WifiManager

        val action = intent.getAction()

        if (!TextUtils.isEmpty(action)) {
            if (intent.getAction().equals(ACTION_WIFI_ON)) {
                // Turns wifi on
                wifiManager.isWifiEnabled = true
                Log.e(TAG, "Turns wifi on")
            } else if (intent.getAction().equals(ACTION_WIFI_OFF)) {
                // Turns wifi off
                wifiManager.isWifiEnabled = false
                Log.e(TAG, "Turns wifi off")
            } else if (intent.getAction().equals(ACTION_CONNECT_TO_WIFI)) {
                // Connects to a specific wifi network
                val networkSSID = intent.getStringExtra("ssid")
                val networkPassword = intent.getStringExtra("password")

                if (!TextUtils.isEmpty(networkSSID) && !TextUtils.isEmpty(networkPassword)) {
                    connectToWifi(networkSSID, networkPassword)
                } else {
                    Log.e(
                        TAG, "onReceive: cannot use " + ACTION_CONNECT_TO_WIFI +
                                "without passing in a proper wifi SSID and password."
                    )
                }
            }
        }
    }

    /**
     * Connect to the specified wifi network.
     *
     * @param networkSSID     - The wifi network SSID
     * @param networkPassword - the wifi password
     *
     * adb shell am broadcast -a android.intent.action.WIFI_ON
     *
     * adb shell am broadcast -a android.intent.action.WIFI_OFF
     *
     * adb shell am broadcast -a android.intent.action.CONNECT_TO_WIFI -e ssid {wifi_ssid} -e password {pwd}
     *
     */
    private fun connectToWifi(networkSSID: String, networkPassword: String) {
        if (!wifiManager.isWifiEnabled) {
            wifiManager.isWifiEnabled = true
        }
        Log.e(TAG, "connectToWifi ssid " + networkSSID + " pass " + networkPassword)

        val conf = WifiConfiguration()
        conf.SSID = String.format("\"%s\"", networkSSID)
        conf.preSharedKey = String.format("\"%s\"", networkPassword)

        val netId = wifiManager.addNetwork(conf)
        wifiManager.disconnect()
        wifiManager.enableNetwork(netId, true)
        wifiManager.reconnect()
    }
}