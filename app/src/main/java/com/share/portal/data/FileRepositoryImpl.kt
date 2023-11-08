package com.share.portal.data

import com.share.portal.data.datasource.OfflineDataSourceImpl
import com.share.portal.data.repository.FileRepository
import com.share.portal.domain.models.FileTreeEntity
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(private val dataSource: OfflineDataSourceImpl)
  : FileRepository {

  override fun getAllExternalFiles(rootPath: String): FileTreeEntity {
    val response = dataSource.getAllExternalFiles(rootPath)
    if (response.data != null) return response.data
    else throw Throwable(response.error)
  }

}