package com.share.portal.presentation.filemanager.wifisharing

import androidx.lifecycle.ViewModel
import com.share.portal.domain.usecase.FileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

}