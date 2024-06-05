package com.share.portal.presentation.filemanager.fileexplorer

sealed class FileExplorerUiState {

  object Loading: FileExplorerUiState()

  object Loaded: FileExplorerUiState()

  object FileSelection: FileExplorerUiState()

  object FileExplorer: FileExplorerUiState()
}