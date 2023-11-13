package com.share.portal.view.filemanager.fileexplorer.model

import com.share.portal.domain.models.ParentFile

data class ParentData(
  val name: String,
  val path: String,
) {
  companion object {
    fun toParentDataList(parent: ParentFile): List<ParentData> {
      return parent.loopParent('/')
    }

    private fun ParentFile.loopParent(delimiter: Char): List<ParentData> {
      val size = path.count { it == delimiter }
      var root = path
      return List(size) {
        root = root.substringAfter(delimiter, "")
        val name = root.substringBefore(delimiter)
        ParentData(
          name = name.ifBlank { delimiter.toString() },
          path = "${path.substringBefore(name)}$name"
        )
      }
    }
  }
}