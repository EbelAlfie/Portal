package com.share.portal.view.filemanager.fileexplorer

import com.share.portal.domain.models.FileTreeEntity

sealed interface FileUiState {
  object Loading: FileUiState

  data class Loaded(
    val files: MutableList<FileTreeEntity> //Linked list or stack?
  ): FileUiState

  data class Error(val cause: Throwable?): FileUiState
}