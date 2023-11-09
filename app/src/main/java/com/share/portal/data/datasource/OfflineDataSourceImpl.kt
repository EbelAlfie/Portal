package com.share.portal.data.datasource

import android.os.Environment
import com.share.portal.data.models.ResponseModel
import com.share.portal.domain.models.FileParam
import com.share.portal.domain.models.FileTreeEntity
import java.io.File
import javax.inject.Inject

class OfflineDataSourceImpl @Inject constructor(): OfflineDataSource {

  override fun getAllExternalFiles(rootPath: String): ResponseModel<FileTreeEntity> {
    return try {
      val rootFile = if (rootPath == FileParam.EXTERNAL.pathName || rootPath.isBlank())
         Environment.getExternalStorageDirectory()
      else File(rootPath)

      val file = FileTreeEntity.createFileTree (
        root = if (rootPath == FileParam.EXTERNAL.pathName) null else rootFile.parentFile,
        current = rootFile?.path,
        child = rootFile.listFiles()?.toList() ?: listOf()
      )

      ResponseModel (
        data = file
      )
    } catch (e: Exception) { /*future*/
      ResponseModel (
        error = Throwable(e.message)
      )
    }
  }

}