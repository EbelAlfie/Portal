package com.share.portal.view.filemanager.fileexplorer

import com.share.portal.domain.models.FileTreeEntity

sealed interface FileUiState {
  object Loading: FileUiState

  data class FileExplore(
    val allFiles: MutableList<FileTreeEntity> //Linked list or stack?
  ): FileUiState

  data class Error(val cause: Throwable?): FileUiState

  data class FileSelect(
    val selectedFile: MutableList<FileTreeEntity>
  ): FileUiState
}