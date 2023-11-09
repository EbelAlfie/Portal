package com.share.portal.view.filemanager.model

import com.share.portal.domain.models.ParentFile

data class ParentData(
  val fileName: String,
  val path: String,
) {
  companion object {
    fun toParentList(parent: ParentFile): List<ParentData> {
      return buildList {
        parent.fileName.substringBeforeLast("/")
      }
    }
  }
}