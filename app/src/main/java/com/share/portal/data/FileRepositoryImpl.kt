package com.share.portal.data

import com.share.portal.data.datasource.OfflineDataSourceImpl
import com.share.portal.data.datasource.OnlineDataSourceImpl
import com.share.portal.data.models.ResponseModel
import com.share.portal.data.repository.FileRepository
import com.share.portal.domain.models.FileTreeEntity
import java.io.File
import java.net.InetSocketAddress
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
  private val offlineSource: OfflineDataSourceImpl,
  private val onlineSource: OnlineDataSourceImpl
  )
  : FileRepository {

  override fun getAllExternalFiles(rootPath: String): FileTreeEntity {
    val response = offlineSource.getAllExternalFiles(rootPath)
    if (response.data != null) return response.data
    else throw Throwable(response.error)
  }

  override fun sendFile(file: File) {
    TODO("Not yet implemented")
  }

  override fun receiveFile(): FileTreeEntity {
    TODO("Not yet implemented")
  }

  override suspend fun connectWithClient(address: InetSocketAddress): Boolean {
    val response = onlineSource.requestConnection(address)
    if (response.data != null) return response.data
    else throw Throwable(response.error)
  }

  override suspend fun establishAsServer() {
    onlineSource.establishWSServer()
  }

}