package com.share.portal.view.filemanager

import android.util.Log
import androidx.lifecycle.ViewModel
import com.share.portal.domain.FileUseCaseImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.InetAddress
import java.net.InetSocketAddress
import javax.inject.Inject

class MainViewModel @Inject constructor(
  private val useCase: FileUseCaseImpl
): ViewModel() {
  private var serverAddr: InetAddress? = null

  fun connectWSClient(address: InetAddress) {
    CoroutineScope(Dispatchers.IO).launch {
      try {
        useCase.connectWithClient(InetSocketAddress(address, 8988))
      } catch (e: Exception) {
        Log.d("CONNECTCLIENT", e.message ?: "")
      }
    }
  }

  fun establishAsServer() {
    CoroutineScope(Dispatchers.IO).launch {
      try {
        serverAddr = useCase.establishAsServer()
      } catch (e: Exception) {

      }
    }
  }

  private fun extractInetAddress(address: InetAddress) {

  }
}