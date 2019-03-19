package com.me.bui.wifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager


/**
 * Created by mao.bui on 3/19/2019.
 */
class ConnectivityReceiver() : BroadcastReceiver(){
    companion object {
        var connectivityReceiverListener: ConnectivityReceiverListener? = null

        fun isConnected(): Boolean {
            val cm = MyApplication.getInstance()!!.applicationContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }

    }

    override fun onReceive(context: Context, arg1: Intent) {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting

        if (connectivityReceiverListener != null) {
            connectivityReceiverListener!!.onNetworkConnectionChanged(isConnected)
        }
    }


    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }
}