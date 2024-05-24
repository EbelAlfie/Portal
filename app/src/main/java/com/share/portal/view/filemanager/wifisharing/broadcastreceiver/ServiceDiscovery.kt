package com.share.portal.view.filemanager.wifisharing.broadcastreceiver

import android.net.wifi.p2p.WifiP2pManager
import android.util.Log

class ServiceDiscovery {
  private fun discoverService() {
    /* Callback includes:
     * fullDomain: full domain name: e.g. "printer._ipp._tcp.local."
     * record: TXT record dta as a map of key/value pairs.
     * device: The device running the advertised service.
     */
    val txtListener = WifiP2pManager.DnsSdTxtRecordListener { fullDomain, record, device ->
      Log.d("DnsSdTxtRecord", "DnsSdTxtRecord available -$record")
    }
  }

}