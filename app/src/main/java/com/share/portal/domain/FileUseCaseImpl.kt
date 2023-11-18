package com.share.portal.domain

import com.share.portal.data.FileRepositoryImpl
import com.share.portal.data.models.ResponseModel
import com.share.portal.domain.models.FileTreeEntity
import com.share.portal.domain.usecase.FileUseCase
import java.io.File
import java.net.InetSocketAddress
import javax.inject.Inject

class FileUseCaseImpl @Inject constructor(private val fileRepository: FileRepositoryImpl): FileUseCase {
  override fun getAllExternalFiles(rootPath: String): FileTreeEntity =
    fileRepository.getAllExternalFiles(rootPath)

  override fun sendFile(file: File) {
    TODO("Not yet implemented")
  }

  override fun receiveFile(): FileTreeEntity {
    TODO("Not yet implemented")
  }

  override suspend fun connectWithClient(address: InetSocketAddress): ResponseModel<Boolean> {
    return fileRepository.connectWithClient(address)
  }

  override suspend fun establishAsServer() {
    fileRepository.establishAsServer()
  }
}