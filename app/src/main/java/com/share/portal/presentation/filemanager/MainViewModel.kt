package com.share.portal.presentation.filemanager

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.share.portal.domain.FileUseCaseImpl
import com.share.portal.domain.usecase.FileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.InetAddress
import java.net.InetSocketAddress
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val useCase: FileUseCase
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
    viewModelScope.launch {
      try {
        serverAddr = useCase.establishAsServer()
      } catch (e: Exception) {

      }
    }
  }

  private fun extractInetAddress(address: InetAddress) {

  }
}