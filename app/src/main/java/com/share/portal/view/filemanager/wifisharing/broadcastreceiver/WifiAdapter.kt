package com.share.portal.view.filemanager.wifisharing.broadcastreceiver

import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager

abstract class WifiAdapter: WifiP2pManager.ActionListener {
  override fun onSuccess() {

  }

  override fun onFailure(p0: Int) {

  }

  abstract fun onConnectionSuccess(device: WifiP2pDevice)

  abstract fun onConnectionFailed(statusCode: Int)
}