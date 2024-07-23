package com.share.portal.presentation.filemanager.wifisharing

import android.net.wifi.p2p.WifiP2pDevice

sealed interface PeerUiState {
  data object Loading: PeerUiState

  data class Loaded(
    val peers: List<WifiP2pDevice> = listOf()
  ) : PeerUiState

  data object EmptyPeer: PeerUiState
}