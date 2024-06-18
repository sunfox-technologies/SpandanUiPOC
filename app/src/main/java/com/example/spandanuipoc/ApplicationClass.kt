package com.example.spandanuipoc

import android.app.Application
import android.widget.Toast
import `in`.sunfox.healthcare.commons.android.spandan_sdk.OnInitializationCompleteListener
import `in`.sunfox.healthcare.commons.android.spandan_sdk.SpandanSDK
import `in`.sunfox.healthcare.commons.android.spandan_sdk.connection.DeviceInfo
import `in`.sunfox.healthcare.commons.android.spandan_sdk.connection.OnDeviceConnectionStateChangeListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class ApplicationClass:Application() {

     private lateinit var spandanSDK: SpandanSDK

    override fun onCreate() {
        super.onCreate()

        try {

            SpandanSDK.initialize(
                application = this,
                verifierToken = BuildConfig.verifierToken,
                masterKey = BuildConfig.masterKey,
                onInitializationCompleteListener = object : OnInitializationCompleteListener {
                    override fun onInitializationFailed(message: String) {
                        Toast.makeText(this@ApplicationClass, message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onInitializationSuccess() {
                        Toast.makeText(this@ApplicationClass, "token initialized...", Toast.LENGTH_SHORT).show()
                        spandanSDK = SpandanSDK.getInstance()
                    }

                }
            )

            SpandanSDK.getInstance().setOnDeviceConnectionStateChangedListener(object : OnDeviceConnectionStateChangeListener{
                override fun onConnectionTimedOut() {

                }

                override fun onDeviceAttached() {

                }

                override fun onDeviceConnected(deviceInfo: DeviceInfo) {
                    CoroutineScope(Main).launch {
                        Toast.makeText(this@ApplicationClass, "device is connected you can start the test now.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onDeviceDisconnected() {
                    Toast.makeText(this@ApplicationClass, "123456 device is connected you can start the test now.", Toast.LENGTH_SHORT).show()
                }

                override fun onUsbPermissionDenied() {

                }

            })
        }catch (e:Exception){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}