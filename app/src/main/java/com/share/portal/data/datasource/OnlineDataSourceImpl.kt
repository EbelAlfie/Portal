package com.share.portal.data.datasource

import android.net.Uri
import com.share.portal.data.dinject.dmodules.WSModule
import com.share.portal.data.models.ResponseModel
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.net.InetSocketAddress
import javax.inject.Inject

class OnlineDataSourceImpl @Inject constructor(
  private val wsInstance: WSModule
): OnlineDataSource {
  private val socket by lazy { wsInstance.provideWSMouth() }
  override suspend fun establishWSServer() {
    val serverSocket = wsInstance.provideWSEars()
    return serverSocket.use {
      val client = serverSocket.accept()
      val inputstream = client.getInputStream()
      serverSocket.close()
    }
  }

  override suspend fun requestConnection(address: InetSocketAddress): ResponseModel<Boolean> {
    return try {
      socket.bind(null)
      socket.connect(address, 500)
      ResponseModel(
        data = true
      )
    } catch (e: FileNotFoundException) {
      ResponseModel(
        error = e
      )
    } catch (e: IOException) {
      ResponseModel(
        error = e
      )
    }
  }

  override suspend fun sendToClient(file: File): ResponseModel<Boolean> {
    val outputStream = socket.getOutputStream()
  }

  override suspend fun closeConnection() {
    socket.takeIf { it.isConnected }?.apply {
      close()
    }
  }
}