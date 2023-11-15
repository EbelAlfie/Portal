package com.share.portal.data.datasource

import android.net.Uri
import android.os.Environment
import com.share.portal.data.models.ResponseModel
import com.share.portal.domain.models.FileTreeEntity
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import javax.inject.Inject

class OnlineDataSourceImpl @Inject constructor(): OnlineDataSource {
//  override fun establishConnectionAsClient() {
//    val serverSocket = ServerSocket(8888)
//    return serverSocket.use {
//
//      val client = serverSocket.accept()
//      val f = File(
//        Environment.getExternalStorageDirectory().absolutePath +
//          "/${context.packageName}/wifip2pshared-${System.currentTimeMillis()}.jpg"
//      )
//      val dirs = File(f.parent)
//
//      dirs.takeIf { it.doesNotExist() }?.apply {
//        mkdirs()
//      }
//      f.createNewFile()
//      val inputstream = client.getInputStream()
//      copyFile(inputstream, FileOutputStream(f))
//      serverSocket.close()
//      f.absolutePath
//    }
//  }

  override fun establishConnectionAsServer(packet: FileTreeEntity) {
    val host: String = ""
    val port: Int = 0
    val socket = Socket()
    try {
      socket.bind(null)
      socket.connect((InetSocketAddress(host, port)), 500)
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