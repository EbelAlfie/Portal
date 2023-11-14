package com.share.portal.view.filemanager.wifisharing.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pManager

class WifiBroadcastReceiver(
  private val p2pManager: WifiP2pManager,
  private val channel: WifiP2pManager.Channel
): BroadcastReceiver() {
  private var actionListener: WifiP2pManager.ActionListener? = null
  private var peerListListener: WifiP2pManager.PeerListListener? = null

  fun setActionListener(listener: WifiP2pManager.ActionListener) {
    actionListener = listener
  }

  fun setPeerListener(listener: WifiP2pManager.PeerListListener) {
    peerListListener = listener
  }

  override fun onReceive(context: Context, intent: Intent) {
    val action: String = intent.action ?: return
    when (action) {
      WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION ->
        checkWifiAvailability(intent)
      WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {}
      WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {}
      WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {}
    }
  }

  private fun checkWifiAvailability(intent: Intent) {
    when (intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)) {
      WifiP2pManager.WIFI_P2P_STATE_ENABLED -> {
        getPeerList()
      }
      else -> {

      }
    }
  }

  private fun getPeerList() {
    p2pManager.requestPeers(channel, peerListListener)
  }

}