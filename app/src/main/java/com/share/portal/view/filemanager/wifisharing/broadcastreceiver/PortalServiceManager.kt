package com.share.portal.view.filemanager.wifisharing.broadcastreceiver

import android.Manifest.permission
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.WifiP2pManager.ActionListener
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest
import android.util.Log
import androidx.annotation.RequiresPermission
import com.share.portal.view.utils.Const

class PortalServiceManager(
  private val wifiP2pManager: WifiP2pManager,
  private val channel: WifiP2pManager.Channel
) {

  @RequiresPermission(allOf = [permission.NEARBY_WIFI_DEVICES, permission.ACCESS_FINE_LOCATION], conditional = true)
  fun openPortal() {
    //  Create a string map containing information about your service.
    val record: Map<String, String> = mapOf(
      "listenport" to Const.SERVER_PORT.toString(),
      "service" to "Portal",
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

    wifiP2pManager.addLocalService(channel, serviceInfo, object: ActionListener {
      override fun onSuccess() {
        Log.d("Portal: add local service", "onSuccess: ")
      }

      override fun onFailure(reason: Int) {
        Log.d("Portal: add local service", "Failed: $reason")
      }

    }) // TODO add listener
  }

  val txtListener = WifiP2pManager.DnsSdTxtRecordListener { fullDomain, record, device ->
    Log.d("DnsSdTxtRecord", "DnsSdTxtRecord available -$record")
  }

  val servListener = WifiP2pManager.DnsSdServiceResponseListener { instanceName, registrationType, resourceType ->
    Log.d("DnsSdTxtRecord 2", "DnsSdTxtRecord available -$instanceName")
  }
  val serviceRequest = WifiP2pDnsSdServiceRequest.newInstance()

  @RequiresPermission(allOf = [permission.NEARBY_WIFI_DEVICES, permission.ACCESS_FINE_LOCATION], conditional = true)
  fun discoverService(peerDiscoverListener: WifiP2pManager.ActionListener? = null) {
    wifiP2pManager.setDnsSdResponseListeners(channel, servListener, txtListener)

    wifiP2pManager.addServiceRequest(
      channel,
      serviceRequest,
      object : WifiP2pManager.ActionListener {
        override fun onSuccess() {
          // Success!
        }

        override fun onFailure(code: Int) {
          // Command failed.  Check for P2P_UNSUPPORTED, ERROR, or BUSY
        }
      }
    )

    wifiP2pManager.discoverServices(
      channel,
      peerDiscoverListener
    )
  }

}