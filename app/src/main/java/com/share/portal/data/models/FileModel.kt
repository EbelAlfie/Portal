package com.share.portal.data.models

import android.os.Parcelable
import com.share.portal.domain.models.FileEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class FileModel(
  val name: String,
  val extension: String //TODO ENUM
) : Parcelable {

  companion object {
    fun transform(files: List<FileModel>): List<FileEntity> =
      files.map {
        FileEntity(
          fileName = it.name,
          extension = it.extension
        )
      }
  }
}
