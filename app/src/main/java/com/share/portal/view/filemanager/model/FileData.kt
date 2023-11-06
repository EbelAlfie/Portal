package com.share.portal.view.filemanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FileData(
  val fileName: String,
  val fileExtension: FileExtension
): Parcelable

enum class FileExtension(extension: String) {
  EXE(".exe")
}
