package com.share.portal.data.datasource

import java.io.FileNotFoundException
import java.io.IOException
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import javax.inject.Inject

class OnlineDataSourceImpl @Inject constructor(): OnlineDataSource {
  private val socket by lazy { Socket() }

  override fun establishWSServer() {
    val serverSocket = ServerSocket(8888)
    return serverSocket.use {
      val client = serverSocket.accept()
      val inputstream = client.getInputStream()
      serverSocket.close()
    }
  }

  override fun requestConnection(address: InetSocketAddress) {
    try {
      socket.bind(null)
      socket.connect(address, 500)
      val outputStream = socket.getOutputStream()
    } catch (e: FileNotFoundException) {
      //catch logic
    } catch (e: IOException) {
      //catch logic
    } finally {
      socket.takeIf { it.isConnected }?.apply {
        close()
      }
    }
  }
}