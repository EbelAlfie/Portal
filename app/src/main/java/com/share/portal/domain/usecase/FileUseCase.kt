package com.share.portal.domain.usecase

import com.share.portal.domain.models.FileTreeEntity
import java.io.File
import java.net.InetSocketAddress

interface FileUseCase {
  fun getAllExternalFiles(rootPath: String = ""): FileTreeEntity

  fun sendFile(file: File)

  fun receiveFile(): FileTreeEntity

  fun connectWithClient(address: InetSocketAddress)
  fun establishAsServer()
}