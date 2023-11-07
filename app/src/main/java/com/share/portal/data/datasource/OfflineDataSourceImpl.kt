package com.share.portal.data.datasource

import android.os.Environment
import com.share.portal.domain.models.FileParam
import com.share.portal.domain.models.FileTreeEntity
import java.io.File
import javax.inject.Inject

class OfflineDataSourceImpl @Inject constructor(): OfflineDataSource {

  override fun getAllExternalFiles(rootPath: String): FileTreeEntity {
    return try {
      val rootFile = if (rootPath == FileParam.EXTERNAL.rootName)
        Environment.getExternalStorageDirectory()
      else File(rootPath)

      FileTreeEntity.createFileTree (
        root = rootFile.parentFile,
        current = rootFile?.path,
        child = rootFile.listFiles()?.toList() ?: listOf()
      )
    } catch (e: Exception) { /*future*/
      FileTreeEntity.createFileTree (
        root = null,
        current = null,
        child = listOf()
      )
    }
  }

}