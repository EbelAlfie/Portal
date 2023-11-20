package com.share.portal.data.datasource

import com.share.portal.data.dinject.dmodules.WSModule
import com.share.portal.data.models.ResponseModel
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import javax.inject.Inject

class OnlineDataSourceImpl @Inject constructor(
  //private val wsModule: WSModule
): OnlineDataSource {

  private val socket = Socket()
  private val serverSocket: ServerSocket by lazy { ServerSocket(0) }
  override suspend fun establishWSServer() {
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
    return try {
      val outputStream = socket.getOutputStream()
      ResponseModel(
        data = true
      )
    }catch (e: Exception) {
      ResponseModel(
        error = e
      )
    }
  }

  override suspend fun closeConnection() {
    socket.takeIf { it.isConnected }?.apply {
      close()
    }
  }
}