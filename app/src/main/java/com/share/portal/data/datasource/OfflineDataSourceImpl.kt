package com.share.portal.data.datasource

import android.util.Log
import com.share.portal.data.models.FileModel
import java.io.File
import javax.inject.Inject

class OfflineDataSourceImpl @Inject constructor(): OfflineDataSource {
  override fun getAllExternalFiles(): List<FileModel> {
    val root = File.listRoots()
    root.forEach {
      it.listFiles()?.forEach {
        Log.d("GGGG", it.path)
      }
    }

    return listOf(
      FileModel("HEllo", "Exe")
    )
  }
}