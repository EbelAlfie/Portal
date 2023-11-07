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
    fun store(files: List<File>?): List<FileEntity> {
      return files?.map {
        FileEntity (
          fileName = it.name,
          extension = it.extension,
          path = it.path,
          size = it.totalSpace, //bytes
          isDirectory = it.isDirectory
        )
      } ?: listOf()
    }
  }
}
