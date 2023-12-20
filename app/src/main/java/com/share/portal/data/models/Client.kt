package com.share.portal.data.models

import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class Client {
  private var socket: Socket? = null
  private var inputStream: InputStream? = null
  private var outputStream: OutputStream? = null

  fun initializeClient() {
    try {
      socket = Socket()
      inputStream = socket?.getInputStream()
      outputStream = socket?.getOutputStream()
    } catch(e: Exception) {
      
    }
  }
}