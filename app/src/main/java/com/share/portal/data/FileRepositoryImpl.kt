package com.share.portal.data

import com.share.portal.data.datasource.OfflineDataSourceImpl
import com.share.portal.data.repository.FileRepository
import java.io.File
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(private val dataSource: OfflineDataSourceImpl)
  : FileRepository {

  override fun getAllExternalFiles(): List<File> {
    return dataSource.getAllExternalFiles()
  }

}