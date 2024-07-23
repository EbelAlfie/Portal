package com.share.portal.presentation.filemanager.wifisharing

sealed interface PeerUiState {
  object Loading: PeerUiState

  data class Loaded(
    val peers: List<Int> = listOf()
  ) : PeerUiState

  data object EmptyPeer: PeerUiState
}