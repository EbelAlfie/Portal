package com.share.portal.view.filemanager.wifisharing.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WpsInfo
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log
import com.share.portal.view.filemanager.wifisharing.adapter.PeerAdapter.PeerConnectionListener

class WifiBroadcastReceiver(
  private val p2pManager: WifiP2pManager,
  private val channel: WifiP2pManager.Channel
): BroadcastReceiver() {
  private var wifiState: Int = WifiP2pManager.WIFI_P2P_STATE_DISABLED

  private var onPeerDiscoveredListener: WifiP2pManager.ActionListener? = null
  private var peerConnectionListener: PeerConnectionListener? = null
  private var peerListListener: WifiP2pManager.PeerListListener? = null
  private var connectionInfoListener: WifiP2pManager.ConnectionInfoListener? = null

  fun setConnectionInfoListener(listener: WifiP2pManager.ConnectionInfoListener) {
    connectionInfoListener = listener
  }

  fun setPeerConnectionListener(listener: PeerConnectionListener) {
    peerConnectionListener = listener
  }

  fun setPeerListener(listener: WifiP2pManager.PeerListListener) {
    peerListListener = listener
  }

  override fun onReceive(context: Context, intent: Intent) {
    val action: String = intent.action ?: return
    when (action) {
      WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION ->{
        checkWifiAvailability(intent)
        Log.d("WIFIGEMINK", "WIFI_P2P_STATE_CHANGED_ACTION ")
      }
      WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
        Log.d("WIFIGEMINK", "WIFI_P2P_PEERS_CHANGED_ACTION ")
        if (wifiState == WifiP2pManager.WIFI_P2P_STATE_DISABLED) return
        getPeerList()
        Log.d("WIFIGEMINK", "WIFI_P2P_PEERS_CHANGED_ACTION ")
      }
      WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
        requestConnectionInfo()
        Log.d("WIFIGEMINK", "WIFI_P2P_CONNECTION_CHANGED_ACTION ")
      }
      WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
        Log.d("WIFIGEMINK", "WIFI_P2P_THIS_DEVICE_CHANGED_ACTION ")
      }
    }
  }

  private fun checkWifiAvailability(intent: Intent) {
    wifiState = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
    when (wifiState) {
      WifiP2pManager.WIFI_P2P_STATE_ENABLED -> {
        discoverPeers()
        Log.d("WIFIGEMINK", "WIFI_P2P_STATE_ENABLED ")
      }
      else -> {
        Log.d("WIFIGEMINK", "${intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)} ")
      }
    }
  }

  private fun getPeerList() = p2pManager.requestPeers(channel, peerListListener)

  fun discoverPeers() = p2pManager.discoverPeers(channel, onPeerDiscoveredListener)

  fun requestConnection(peer: WifiP2pDevice) {
    val config = WifiP2pConfig()
    config.deviceAddress = peer.deviceAddress
    config.wps.setup = WpsInfo.PBC
    p2pManager.connect(channel, config, object: WifiP2pManager.ActionListener {
      override fun onSuccess() { peerConnectionListener?.onConnectionSuccess(peer) }
      override fun onFailure(reason: Int) { peerConnectionListener?.onConnectionFailed(reason) }
    })
  }

  fun requestConnectionInfo() =
    p2pManager.requestConnectionInfo(channel, connectionInfoListener)

}