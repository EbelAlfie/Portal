package com.share.portal.view.filemanager.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.share.portal.R
import com.share.portal.domain.models.FileTreeEntity
import com.share.portal.view.filemanager.model.FileExtension.Companion.convertExtension
import com.share.portal.view.filemanager.model.FileExtension.FOLDER
import kotlinx.parcelize.Parcelize

@Parcelize
data class FileData(
  val fileName: String,
  val extension: FileExtension,
  val path: String,
  val size: Long,
  val isParent: Boolean,
): Parcelable {

  companion object {
    fun store(fileTree: FileTreeEntity): MutableList<FileData> {
      val fileList = mutableListOf<FileData>()

      fileTree.root?.let {
        fileList.add(generateParentData(it.path, it.fileName))
      }
      fileList.addAll(
        fileTree.child.map {
          FileData(
            fileName = it.name,
            extension = convertExtension(it.extension),
            path = it.path,
            size = it.totalSpace, //bytes
            isParent = false,
          )
        }
      )

      return fileList
    }

    private fun generateParentData(path: String, name: String): FileData {
      return FileData(
        fileName = name,
        extension = FOLDER,
        path = path,
        size = 0L, //bytes
        isParent = true,
      )
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
