package com.share.portal.data.datasource

import com.share.portal.data.models.ResponseModel
import java.io.File
import java.net.InetAddress
import java.net.InetSocketAddress

interface OnlineDataSource {

  //fun establishConnectionAsClient(): ResponseModel<FileTreeEntity>

  suspend fun establishWSServer(): InetAddress
  suspend fun requestClientConnection(address: InetSocketAddress): ResponseModel<Boolean>
  suspend fun closeConnection()
  suspend fun sendToClient(file: File): ResponseModel<Boolean>
}