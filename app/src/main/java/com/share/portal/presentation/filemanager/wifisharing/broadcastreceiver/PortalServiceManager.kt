package com.share.portal.presentation.filemanager.wifisharing.broadcastreceiver

import android.Manifest.permission
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest
import android.util.Log
import androidx.annotation.RequiresPermission
import com.share.portal.presentation.utils.Const

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

    wifiP2pManager.addLocalService(channel, serviceInfo, null) // TODO add listener
  }

  @RequiresPermission(allOf = [permission.NEARBY_WIFI_DEVICES, permission.ACCESS_FINE_LOCATION], conditional = true)
  fun discoverService(
    onPeerDiscovered: (WifiP2pDevice) -> Unit
  ) {
    /* Callback includes:
     * fullDomain: full domain name: e.g. "printer._ipp._tcp.local."
     * record: TXT record dta as a map of key/value pairs.
     * device: The device running the advertised service.
     */

    val txtListener = WifiP2pManager.DnsSdTxtRecordListener { fullDomain, record, device ->
      Log.d("DnsSdTxtRecord", "DnsSdTxtRecord available -$record")
      onPeerDiscovered.invoke(device)
    }

    val servListener = WifiP2pManager.DnsSdServiceResponseListener { instanceName, registrationType, resourceType ->

    }
    wifiP2pManager.setDnsSdResponseListeners(channel, servListener, txtListener)

    val serviceRequest = WifiP2pDnsSdServiceRequest.newInstance()
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
      object : WifiP2pManager.ActionListener {
        override fun onSuccess() {
          // Success!
        }

        override fun onFailure(code: Int) {
          // Command failed. Check for P2P_UNSUPPORTED, ERROR, or BUSY
          when (code) {
            WifiP2pManager.P2P_UNSUPPORTED -> {
              Log.d("Error", "Wi-Fi Direct isn't supported on this device.")
            }
          }
        }
      }
    )
  }

}