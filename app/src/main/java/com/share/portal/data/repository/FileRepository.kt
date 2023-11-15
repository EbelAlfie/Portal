package com.share.portal.data.repository

import com.share.portal.domain.models.FileTreeEntity
import java.io.File
import java.net.InetSocketAddress

interface FileRepository {
  fun getAllExternalFiles(rootPath: String = ""): FileTreeEntity

  fun sendFile(file: File)

  fun receiveFile(): FileTreeEntity

  fun connectWithClient(address: InetSocketAddress)
  fun establishAsServer()
}