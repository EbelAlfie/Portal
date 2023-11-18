package com.share.portal.domain.usecase

import com.share.portal.data.models.ResponseModel
import com.share.portal.domain.models.FileTreeEntity
import java.io.File
import java.net.InetSocketAddress

interface FileUseCase {
  fun getAllExternalFiles(rootPath: String = ""): FileTreeEntity

  fun sendFile(file: File)

  fun receiveFile(): FileTreeEntity

  suspend fun connectWithClient(address: InetSocketAddress): ResponseModel<Boolean>
  suspend fun establishAsServer()
}