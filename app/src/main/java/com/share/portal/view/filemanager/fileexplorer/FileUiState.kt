package com.share.portal.view.filemanager.fileexplorer

import com.share.portal.domain.models.FileTreeEntity

sealed interface FileUiState {
  object Loading: FileUiState

  data class FileExplore(
    val allFiles: MutableList<FileTreeEntity> //Linked list or stack?
  ): FileUiState

  data class FileSelect(
    val allFiles: MutableList<FileTreeEntity>,
    val selectedIndices: MutableList<Int>
  ): FileUiState

  data class Error(val cause: Throwable?): FileUiState
}

sealed interface FileAction {
  data class ClickFile(
    val filePosition: Int
  ): FileAction

  data class SelectFile(
    val filePosition: Int
  ): FileAction
}