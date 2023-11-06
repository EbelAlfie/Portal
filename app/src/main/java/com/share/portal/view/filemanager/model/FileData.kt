package com.share.portal.view.filemanager.model

import android.os.Parcelable
import com.share.portal.domain.models.FileEntity
import com.share.portal.view.filemanager.model.FileExtension.Companion.convertExtension
import kotlinx.parcelize.Parcelize
import java.io.File
import java.security.cert.Extension

@Parcelize
data class FileData(
  val fileName: String,
  val extension: FileExtension,
  val path: String,
  val size: Long,
  val isDirectory: Boolean
): Parcelable {

  companion object {
    fun store(it: File): FileData {
      return FileData(
        fileName = it.name,
        extension = convertExtension(it.extension),
        path = it.path,
        size = it.totalSpace, //bytes
        isDirectory = it.isDirectory
      )
    }
  }

}

enum class FileExtension(extension: String) {
  EXE(".exe");

  companion object {
    fun convertExtension(ext: String): FileExtension {

    }
  }
}
