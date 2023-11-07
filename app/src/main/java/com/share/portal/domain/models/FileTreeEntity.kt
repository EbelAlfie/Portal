package com.share.portal.domain.models

import java.io.File

data class ParentFile (
  val fileName: String = "...",
  val path: String
) {
  companion object {
    fun createParent(parentFile: File?): ParentFile {
      return ParentFile(
        fileName = parentFile?.name ?: "...",
        path = parentFile?.path ?: ""
      )
    }
  }
}

data class FileTreeEntity(
  val root: ParentFile, //create custom file
  val current: String,
  val child: List<File>
) {
  companion object {
    fun createFileTree(
      root: File?,
      current: String?,
      child: List<File>
    ): FileTreeEntity {
      return FileTreeEntity(
        root = ParentFile.createParent(root),
        current = current ?: "",
        child = child
      )
    }
  }
}
