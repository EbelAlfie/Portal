package com.share.portal.data

import com.share.portal.data.datasource.OfflineDataSourceImpl
import com.share.portal.data.models.FileModel
import com.share.portal.data.repository.FileRepository
import com.share.portal.domain.models.FileEntity
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(private val dataSource: OfflineDataSourceImpl)
  : FileRepository {

  override fun getAllExternalFiles(): List<FileEntity> {
    val files = dataSource.getAllExternalFiles()
    return FileModel.transform(files)
  }

}