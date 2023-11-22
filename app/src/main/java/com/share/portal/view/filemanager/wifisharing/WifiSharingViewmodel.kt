package com.share.portal.view.filemanager.wifisharing

import androidx.lifecycle.ViewModel
import com.share.portal.domain.usecase.FileUseCase
import java.io.File
import javax.inject.Inject

class WifiSharingViewmodel @Inject constructor(
  private val useCase: FileUseCase
): ViewModel() {
  private var rootPath: String = ""

  fun requestClientFiles () {
    rootPath
  }

  fun sendFile(filePacket: File) {
    useCase.sendFile(filePacket)
  }

  fun receiveFile() {
    useCase.receiveFile()
  }

}