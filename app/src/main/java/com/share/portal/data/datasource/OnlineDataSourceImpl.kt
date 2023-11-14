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
  override fun establishConnectionAsClient() {
    val serverSocket = ServerSocket(8888)
    return serverSocket.use {

      val client = serverSocket.accept()
      val f = File(
        Environment.getExternalStorageDirectory().absolutePath +
        "/${context.packageName}/wifip2pshared-${System.currentTimeMillis()}.jpg")
      val dirs = File(f.parent)

      dirs.takeIf { it.doesNotExist() }?.apply {
        mkdirs()
      }
      f.createNewFile()
      val inputstream = client.getInputStream()
      copyFile(inputstream, FileOutputStream(f))
      serverSocket.close()
      f.absolutePath
  }

  override fun establishConnectionAsServer(packet: FileTreeEntity) {
    val context = applicationContext
    val host: String
    val port: Int
    val len: Int
    val socket = Socket()
    val buf = ByteArray(1024)
    ...
    try {
      /**
       * Create a client socket with the host,
       * port, and timeout information.
       */
      socket.bind(null)
      socket.connect((InetSocketAddress(host, port)), 500)

      /**
       * Create a byte stream from a JPEG file and pipe it to the output stream
       * of the socket. This data is retrieved by the server device.
       */
      val outputStream = socket.getOutputStream()
      val cr = context.contentResolver
      val inputStream: InputStream = cr.openInputStream(Uri.parse("path/to/picture.jpg"))
      while (inputStream.read(buf).also { len = it } != -1) {
        outputStream.write(buf, 0, len)
      }
      outputStream.close()
      inputStream.close()
    } catch (e: FileNotFoundException) {
      //catch logic
    } catch (e: IOException) {
      //catch logic
    } finally {
      /**
       * Clean up any open sockets when done
       * transferring or if an exception occurred.
       */
      socket.takeIf { it.isConnected }?.apply {
        close()
      }
    }
  }
  }
}