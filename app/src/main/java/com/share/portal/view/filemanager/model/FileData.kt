package com.share.portal.view.filemanager.model

import android.os.Parcelable
import com.share.portal.domain.models.FileEntity
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class FileData(
  val fileName: String,
  val fileExtension: FileExtension
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

enum class FileExtension(extension: String) {
  EXE(".exe")
}
