package com.share.portal.view.filemanager.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.share.portal.R
import com.share.portal.view.filemanager.model.FileExtension.Companion.convertExtension
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class FileData(
  val fileName: String,
  val extension: FileExtension,
  val path: String,
  val size: Long,
  val isDirectory: Boolean,
  val isParent: Boolean
): Parcelable {

  companion object {
    fun store(files: List<File>): List<FileData> {
      return files.map {
        FileData(
          fileName = it.name,
          extension = convertExtension(it.extension),
          path = it.path,
          size = it.totalSpace, //bytes
          isDirectory = it.isDirectory,
          isParent = true
        )
      }
    }
  }

}

enum class FileExtension(@DrawableRes val icon: Int) {
  IMG(R.drawable.ic_image),
  AUDIO(R.drawable.ic_audio),
  FOLDER(R.drawable.ic_folder),
  DOCUMENT(R.drawable.ic_document);

  companion object {
    fun convertExtension(ext: String): FileExtension { //TODO enhance
      return when (ext.lowercase()) {
        "jpg", "jpeg", "png", "webp" -> IMG
        "mp3", "wav", "flac" -> AUDIO
        "" -> FOLDER
        else -> DOCUMENT
      }
    }

  }
}
