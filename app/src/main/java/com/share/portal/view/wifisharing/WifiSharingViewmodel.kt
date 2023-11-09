package com.share.portal.view.wifisharing

import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pManager
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class WifiSharingViewmodel @Inject constructor(): ViewModel() {
  fun getIntentFilter() = IntentFilter().apply {
    addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
    addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
    addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
    addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
  }

}