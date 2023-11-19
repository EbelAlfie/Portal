package com.share.portal.data.repository

import com.share.portal.domain.models.FileTreeEntity
import java.io.File
import java.net.InetSocketAddress

interface FileRepository {
  suspend fun getAllExternalFiles(rootPath: String = ""): FileTreeEntity

  fun sendFile(file: File)

  fun receiveFile(): FileTreeEntity

  suspend fun connectWithClient(address: InetSocketAddress): Boolean
  suspend fun establishAsServer()
}