package com.share.portal.view.filemanager.fileexplorer

import com.share.portal.domain.models.FileTreeEntity

sealed interface FileUiState {
  object Loading: FileUiState

  data class Loaded(
    val allFiles: List<FileTreeEntity>, //Linked list or stack?
    val operationMode: OperationMode = OperationMode.FileExplore
  ): FileUiState

  data class Error(val cause: Throwable?): FileUiState
}

sealed interface OperationMode {
  data class FileSelect(
    val allFiles: MutableList<FileTreeEntity>,
    val selectedIndices: MutableList<Int>
  ): OperationMode

  object FileExplore: OperationMode
}

sealed interface FileAction {
  data class ClickFile(
    val filePosition: Int
  ): FileAction

  data class SelectFile(
    val filePosition: Int
  ): FileAction
}