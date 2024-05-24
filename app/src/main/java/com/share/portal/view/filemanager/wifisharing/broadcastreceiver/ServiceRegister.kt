package com.share.portal.view.filemanager.wifisharing.broadcastreceiver

import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo
import com.share.portal.view.utils.Const

class ServiceRegister(
  private val wifiP2pManager: WifiP2pManager,
  private val channel: WifiP2pManager.Channel
) {

  private fun startRegistration() {
    //  Create a string map containing information about your service.
    val record: Map<String, String> = mapOf(
      "listenport" to Const.SERVER_PORT.toString(),
      "buddyname" to "John Doe${(Math.random() * 1000).toInt()}",
      "available" to "visible"
    )

    // Service information.  Pass it an instance name, service type
    // _protocol._transportlayer , and the map containing
    // information other devices will want once they connect to this one.
    val serviceInfo =
      WifiP2pDnsSdServiceInfo.newInstance("_portal", "_presence._tcp", record)

    // Add the local service, sending the service info, network channel,
    // and listener that will be used to indicate success or failure of
    // the request.

    wifiP2pManager.addLocalService(channel, serviceInfo, object : WifiP2pManager.ActionListener {
      override fun onSuccess() {
        // Command successful! Code isn't necessarily needed here,
        // Unless you want to update the UI or add logging statements.
      }

      override fun onFailure(arg0: Int) {
        // Command failed.  Check for P2P_UNSUPPORTED, ERROR, or BUSY
      }
    })
  }
}