package com.share.portal.view.wifisharing.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pManager

class WifiManager: BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {
    val action: String = intent?.action ?: return
    when (action) {
      WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
        when (intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)) {
          WifiP2pManager.WIFI_P2P_STATE_ENABLED -> {
          }
          else -> {
          }
        }
      }
    }
  }

}