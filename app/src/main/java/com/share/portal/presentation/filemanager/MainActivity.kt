package com.share.portal.presentation.filemanager

import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.share.portal.presentation.filemanager.fileexplorer.FileExplorerPage
import com.share.portal.presentation.filemanager.fileexplorer.FileViewModel
import com.share.portal.presentation.filemanager.wifisharing.PeerFinderPage
import com.share.portal.presentation.filemanager.wifisharing.broadcastreceiver.WifiBroadcastReceiver
import com.share.portal.presentation.ui.theme.Portal_BlueTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), WifiPerantara {

  private val viewModel: MainViewModel by viewModels()

  private val fileViewModel: FileViewModel by viewModels()

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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Portal_BlueTheme {
        PagerScreen(
          pageFactory = listOf(FileExplorerPage(fileViewModel), PeerFinderPage())
        )
      }
    }
  }

  private fun initializeAsServer() {
    viewModel.establishAsServer()
  }

  override fun registerWifi() {
    registerReceiver(wifiBroadcastReceiver, wifiIntentFilter)
    wifiBroadcastReceiver.openPortal()
  }

  override fun unregisterWifi() = unregisterReceiver(wifiBroadcastReceiver)

  override fun getP2pService() = wifiBroadcastReceiver
}