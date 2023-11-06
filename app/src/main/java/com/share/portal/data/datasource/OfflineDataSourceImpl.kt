package com.share.portal.data.datasource

import android.os.Environment
import android.util.Log
import com.share.portal.domain.models.FileEntity
import javax.inject.Inject

class OfflineDataSourceImpl @Inject constructor(): OfflineDataSource {
  override fun getAllExternalFiles(): List<FileEntity> {
    val root = Environment.getExternalStorageDirectory()
    Log.d("FILES", root.path)
    root.listFiles()?.forEach {
      Log.d("FILES", it.path)
    }

    return root.listFiles()?.map {
      FileEntity(it.nameWithoutExtension, it.extension)
    } ?: listOf()
  }
}