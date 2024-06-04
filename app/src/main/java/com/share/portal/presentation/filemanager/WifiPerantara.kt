package com.share.portal.presentation.filemanager

import android.content.IntentFilter
import com.share.portal.view.filemanager.wifisharing.broadcastreceiver.WifiBroadcastReceiver

interface WifiPerantara {
  val wifiIntentFilter: IntentFilter
  fun registerWifi()
  fun unregisterWifi()

  fun getP2pService(): WifiBroadcastReceiver
}