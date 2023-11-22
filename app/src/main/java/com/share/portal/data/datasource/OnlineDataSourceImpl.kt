package com.share.portal.data.datasource

import android.util.Log
import com.share.portal.data.models.ResponseModel
import java.io.File
import java.io.FileNotFoundException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import javax.inject.Inject

class OnlineDataSourceImpl @Inject constructor(
  //private val wsModule: WSModule
): OnlineDataSource {

  private val clientSocket = Socket()
  private val serverSocket: ServerSocket by lazy { ServerSocket(8988) }

  /** As Server **/
  override suspend fun establishWSServer(): InetAddress {
//    serverSocket.use {
//      val client = serverSocket.accept()
//      val inputstream = client.getInputStream()
//      serverSocket.close()
//    }
    return serverSocket.inetAddress
  }

  fun onRequestReceived() {
    val inputStream = clientSocket.getInputStream()
  }

  /** As Client **/

  override suspend fun requestClientConnection(address: InetSocketAddress): ResponseModel<Boolean> {
    return try {
      Log.d("AIUEO", "requestConnection:")
      clientSocket.bind(null)
      clientSocket.connect(address, 500)
      Log.d("AIUEO", "requestConnection: ")
      ResponseModel(
        data = true
      )
    } catch (e: FileNotFoundException) {
      ResponseModel(
        error = e
      )
    } catch (e: Exception) {
      ResponseModel(
        error = e
      )
    }
  }

  //After connected, request client's files
  fun sendFileRequestToClient(root: String): ResponseModel<Boolean> {
    return try {
      val outputStream = clientSocket.getOutputStream()
      outputStream.write(root.toByteArray())
      ResponseModel(
        data = true
      )
    }catch (e: Exception) {
      ResponseModel(
        error = e
      )
    }
  }

  override suspend fun sendToClient(file: File): ResponseModel<Boolean> {
    return try {
      val outputStream = clientSocket.getOutputStream()
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
    clientSocket.takeIf { it.isConnected }?.apply {
      close()
    }
  }
}