package com.share.portal.view.filemanager.wifisharing.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager

class WifiBroadcastReceiver(
  private val p2pManager: WifiP2pManager,
  private val channel: WifiP2pManager.Channel
): BroadcastReceiver() {
  private var connectListener: WifiP2pManager.ActionListener? = null
  private var peerListListener: WifiP2pManager.PeerListListener? = null
  private var channelListener: WifiP2pManager.ConnectionInfoListener? = null

  fun setConnectListener(listener: WifiP2pManager.ActionListener) {
    connectListener = listener
  }

  fun setPeerListener(listener: WifiP2pManager.PeerListListener) {
    peerListListener = listener
  }

  override fun onReceive(context: Context, intent: Intent) {
    val action: String = intent.action ?: return
    when (action) {
      WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION ->
        checkWifiAvailability(intent)
      WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
        getPeerList()
      }
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

  private fun getPeerList() = p2pManager.requestPeers(channel, peerListListener)

  fun discoverPeers() = p2pManager.discoverPeers(channel, connectListener)

  fun requestConnection(peer: WifiP2pDevice) {
    val config = WifiP2pConfig()
    config.deviceAddress = peer.deviceAddress
    p2pManager.connect(channel, config, connectListener)
  }

}