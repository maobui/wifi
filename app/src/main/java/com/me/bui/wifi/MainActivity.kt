package com.me.bui.wifi

import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mWifiConnectionReceiver: WifiConnectionReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mWifiConnectionReceiver = WifiConnectionReceiver()
        registerReceiver(mWifiConnectionReceiver, IntentFilter(WifiConnectionReceiver.getIntentFilterForWifiConnectionReceiver()))
    }

    override fun onDestroy() {
        unregisterReceiver(mWifiConnectionReceiver)
        super.onDestroy()
    }
}
