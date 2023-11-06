package com.share.portal.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class FileEntity(
  val fileName: String,
  val extension: String,
  val path: String,
  val size: Long,
  val isDirectory: Boolean
): Parcelable {
  companion object {
    fun store(it: File): FileEntity {
      return FileEntity(
        fileName = it.name,
        extension = it.extension,
        path = it.path,
        size = it.totalSpace, //bytes
        isDirectory = it.isDirectory
      )
    }
  }
}
