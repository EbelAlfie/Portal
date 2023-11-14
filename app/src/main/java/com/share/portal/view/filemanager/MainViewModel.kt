package com.share.portal.view.filemanager

import androidx.lifecycle.ViewModel
import com.share.portal.domain.FileUseCaseImpl
import java.io.File
import javax.inject.Inject

class MainViewModel @Inject constructor(
  private val useCase: FileUseCaseImpl
): ViewModel() {
  fun sendFile(filePacket: File) {
    useCase.sendFile(filePacket)
  }

  fun receiveFile() {
    useCase.receiveFile()
  }
}