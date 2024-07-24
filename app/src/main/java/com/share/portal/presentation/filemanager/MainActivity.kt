package com.share.portal.presentation.filemanager

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.p2p.WifiP2pManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import com.share.portal.presentation.filemanager.fileexplorer.FileExploreScreen
import com.share.portal.presentation.filemanager.fileexplorer.FileExplorerPage
import com.share.portal.presentation.filemanager.fileexplorer.FileViewModel
import com.share.portal.presentation.filemanager.wifisharing.PeerFinderPage
import com.share.portal.presentation.filemanager.wifisharing.PeersScreen
import com.share.portal.presentation.filemanager.wifisharing.WifiSharingViewmodel
import com.share.portal.presentation.filemanager.wifisharing.broadcastreceiver.WifiBroadcastReceiver
import com.share.portal.presentation.ui.theme.Portal_BlueTheme
import com.share.portal.presentation.utils.PermissionChecker
import com.share.portal.presentation.utils.PermissionState
import com.share.portal.presentation.utils.PermissionUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), WifiPerantara {

  /** ViewModels **/
  private val viewModel: MainViewModel by viewModels()

  private val fileViewModel: FileViewModel by viewModels()

  private val wifiViewModel: WifiSharingViewmodel by viewModels()

  /** Wifi services */
  override val wifiIntentFilter: IntentFilter
    get() = IntentFilter().apply {
      addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
      addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
      addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
      addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
    }

  private val wifiP2PManager: WifiP2pManager by lazy(LazyThreadSafetyMode.NONE) {
    getSystemService(WIFI_P2P_SERVICE) as WifiP2pManager
  }

  private val wifiBroadcastReceiver: WifiBroadcastReceiver by lazy {
    WifiBroadcastReceiver(
      wifiP2PManager,
      wifiP2PManager.initialize(this, mainLooper, null)
    )
  }

  @OptIn(ExperimentalFoundationApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Portal_BlueTheme {
        var permissionState = remember { PermissionState() }

        PagerScreen(
          pageFactory = listOf(FileExplorerPage(), PeerFinderPage())
        ) { page ->
          when (page) {
            Page.FileExplorer ->
              FileExploreScreen(
                fileViewModel = fileViewModel
              )

            Page.FileSharing ->
              PeersScreen(
                viewModel = wifiViewModel,
                discoverPeers = {
                  permissionState = PermissionState(
                    permissions = PermissionUtils.getWifiSharingPermission(),
                    onPermissionGranted = ::initiateDiscovery
                  )
                }
              )
          }
        }

        PermissionChecker(permissionState)

        permissionState = PermissionState(
          permissions = listOf(PermissionUtils.FINE_LOCATION),
          onPermissionGranted = ::registerWifi
        )
      }
    }
  }


  @SuppressLint("MissingPermission")
  private fun initiateDiscovery() {
    wrapWithPermission {
      wifiBroadcastReceiver.initiatePeerDiscovery(wifiViewModel::onPeerDiscovered)
    }
  }

  override fun registerWifi() {
    if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU)
      registerReceiver(wifiBroadcastReceiver, wifiIntentFilter, RECEIVER_NOT_EXPORTED)
    else
      registerReceiver(wifiBroadcastReceiver, wifiIntentFilter)

    wrapWithPermission(wifiBroadcastReceiver::openPortal)
  }

  private fun wrapWithPermission(action: () -> Unit) {
    if (ActivityCompat.checkSelfPermission(
        this,
        permission.ACCESS_FINE_LOCATION
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      return
    }
    action()
  }

  override fun unregisterWifi() = unregisterReceiver(wifiBroadcastReceiver)

  override fun getP2pService() = wifiBroadcastReceiver
}