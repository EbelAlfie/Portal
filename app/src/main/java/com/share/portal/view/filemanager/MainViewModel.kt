package com.share.portal.view.filemanager

import androidx.lifecycle.ViewModel
import com.share.portal.domain.FileUseCaseImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.InetSocketAddress
import javax.inject.Inject

class MainViewModel @Inject constructor(
  private val useCase: FileUseCaseImpl
): ViewModel() {

  fun connectWSClient(host: String, port: Int) {
    CoroutineScope(Dispatchers.IO).launch {
      try {
        useCase.connectWithClient(InetSocketAddress(host, port))
      } catch (e: Exception) {

      }
    }
  }

  fun establishAsServer() {
    CoroutineScope(Dispatchers.IO).launch {
      useCase.establishAsServer()
    }
  }
}