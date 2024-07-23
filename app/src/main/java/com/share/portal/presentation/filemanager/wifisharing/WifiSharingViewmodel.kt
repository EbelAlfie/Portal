package com.share.portal.presentation.filemanager.wifisharing

import android.net.wifi.p2p.WifiP2pDevice
import androidx.lifecycle.ViewModel
import com.share.portal.domain.usecase.FileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import javax.inject.Inject

@HiltViewModel
class WifiSharingViewmodel @Inject constructor(
  private val useCase: FileUseCase
): ViewModel() {

  private val _uiState: MutableStateFlow<PeerUiState> = MutableStateFlow(PeerUiState.Loading)
  val uiState: StateFlow<PeerUiState> = _uiState.asStateFlow()

  fun requestClientFiles () {

  }

  fun sendFile(filePacket: File) {
    useCase.sendFile(filePacket)
  }

  fun receiveFile() {
    useCase.receiveFile()
  }

  fun onPeerDiscovered(wifiP2pDevice: WifiP2pDevice) {
    _uiState.update {
      PeerUiState.Loaded(
        listOf(wifiP2pDevice)
      )
    }
  }

}