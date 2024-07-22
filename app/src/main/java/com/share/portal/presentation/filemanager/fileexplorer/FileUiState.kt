package com.share.portal.presentation.filemanager.fileexplorer

import com.share.portal.domain.models.FileTreeEntity
import com.share.portal.presentation.filemanager.fileexplorer.model.FileData

sealed interface FileUiState {
  data object Loading: FileUiState

  data class FileScreen(
    val allFiles: List<FileTreeEntity>, //Linked list or stack?
    val previewMode: PreviewMode = PreviewMode.Explore()
  ): FileUiState

  data class Error(val cause: Throwable?): FileUiState
}

sealed class PreviewMode(
  var allFiles: List<FileData> = listOf()
) {
  data class Explore(
    val files: List<FileData> = listOf()
  ): PreviewMode(files)

  data class Select(
    val files: List<FileData> = listOf()
  ): PreviewMode(files)
}