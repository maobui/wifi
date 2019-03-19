package com.me.bui.wifi

import android.app.Application


/**
 * Created by mao.bui on 3/19/2019.
 */
class MyApplication : Application() {

    companion object {
        private lateinit var mInstance: MyApplication

        @Synchronized
        fun getInstance(): MyApplication {
            return mInstance
        }

    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    fun setConnectivityListener(listener: ConnectivityReceiver.ConnectivityReceiverListener) {
        ConnectivityReceiver.connectivityReceiverListener = listener
    }
}