package com.share.portal.presentation.filemanager.fileexplorer.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.share.portal.R
import com.share.portal.domain.models.FileTreeEntity
import com.share.portal.view.filemanager.fileexplorer.model.FileExtension.Companion.convertExtension
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class FileData(
  val file: File,
  val isSelected: Boolean = false,
  val extension: FileExtension,
): Parcelable {

  companion object {
    fun store(fileTree: FileTreeEntity): List<FileData> {
      return fileTree.child.map {
        FileData(
          file = it,
          extension = convertExtension(it.extension)
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
