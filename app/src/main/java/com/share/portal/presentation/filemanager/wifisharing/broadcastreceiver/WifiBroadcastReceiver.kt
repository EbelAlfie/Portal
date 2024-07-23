package com.share.portal.presentation.filemanager.wifisharing.broadcastreceiver

import android.Manifest.permission
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log
import androidx.annotation.RequiresPermission

class WifiBroadcastReceiver(
  private val p2pManager: WifiP2pManager,
  private val channel: WifiP2pManager.Channel
): BroadcastReceiver() {
  private val portalServiceManager: PortalServiceManager by lazy {
    PortalServiceManager(p2pManager, channel)
  }

  private var wifiState: Int = WifiP2pManager.WIFI_P2P_STATE_DISABLED

  private var onPeerDiscoveredListener: WifiP2pManager.ActionListener? = null
  //private var peerConnectionListener: PeerConnectionListener? = null
  private var peerListListener: WifiP2pManager.PeerListListener? = null
  private var connectionInfoListener: WifiP2pManager.ConnectionInfoListener? = null

  fun setConnectionInfoListener(listener: WifiP2pManager.ConnectionInfoListener) {
    connectionInfoListener = listener
  }

//  fun setPeerConnectionListener(listener: PeerConnectionListener) {
//    peerConnectionListener = listener
//  }

  fun setPeerListener(listener: WifiP2pManager.PeerListListener) {
    peerListListener = listener
  }

  @RequiresPermission(allOf = [permission.NEARBY_WIFI_DEVICES, permission.ACCESS_FINE_LOCATION], conditional = true)
  fun initiatePeerDiscovery(onPeerDiscovered: (WifiP2pDevice) -> Unit) =
    portalServiceManager.discoverService(onPeerDiscovered)

  @RequiresPermission(allOf = [permission.NEARBY_WIFI_DEVICES, permission.ACCESS_FINE_LOCATION], conditional = true)
  fun openPortal() = portalServiceManager.openPortal()

  fun requestConnection(peer: WifiP2pDevice) {

  }

  override fun onReceive(context: Context, intent: Intent) {
    val action: String = intent.action ?: return
    when (action) {
      WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION ->
        checkWifiAvailability(intent)
      WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> { //Peer discovery success
        if (wifiState == WifiP2pManager.WIFI_P2P_STATE_DISABLED) return
        getPeerList()
      }
      WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION ->
        requestConnectionInfo()
      WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {}
    }
  }

  private fun checkWifiAvailability(intent: Intent) {
    wifiState = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
    when (wifiState) {
      WifiP2pManager.WIFI_P2P_STATE_ENABLED -> {
//        initiatePeerDiscovery()
        Log.d("WIFIGEMINK", "WIFI_P2P_STATE_ENABLED ")
      }
      else -> {
        Log.d("WIFIGEMINK", "${intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)} ")
      }
    }
  }

  private fun getPeerList() {}

  private fun requestConnectionInfo() {} //= p2pManager.requestConnectionInfo(channel, connectionInfoListener)

}