package com.share.portal.data.datasource

import android.os.Environment
import android.util.Log
import java.io.File
import javax.inject.Inject

class OfflineDataSourceImpl @Inject constructor(): OfflineDataSource {
  override fun getAllExternalFiles(): List<File> {
    val root = Environment.getExternalStorageDirectory()
    Log.d("FILES", root.path)
    root.listFiles()?.forEach {
      Log.d("FILES", it.path)
    }

    return root.listFiles()?.toList() ?: listOf()
  }
}