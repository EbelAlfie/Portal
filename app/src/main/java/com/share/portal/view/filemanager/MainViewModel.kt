package com.share.portal.view.filemanager

import androidx.lifecycle.ViewModel
import com.share.portal.domain.FileUseCaseImpl
import java.net.InetSocketAddress
import javax.inject.Inject

class MainViewModel @Inject constructor(
  private val useCase: FileUseCaseImpl
): ViewModel() {

  fun connectWithClient(host: String, port: Int) {
    useCase.connectWithClient(InetSocketAddress(host, port))
  }

  fun establishAsServer() {
    useCase.establishAsServer()
  }
}