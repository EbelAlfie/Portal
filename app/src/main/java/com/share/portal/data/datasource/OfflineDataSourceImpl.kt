package com.share.portal.data.datasource

import android.os.Environment
import java.io.File
import javax.inject.Inject

class OfflineDataSourceImpl @Inject constructor(): OfflineDataSource {

  override fun getAllExternalFiles(rootPath: String): List<File> {
    return try {
      val parent = if (rootPath.isBlank())
        Environment.getExternalStorageDirectory()
      else File(rootPath)

      val files = mutableListOf(parent)
      parent.listFiles()?.let { files.addAll(it) }
      files
    } catch (e: Exception) { /*future*/
      listOf()
    }

  }

}