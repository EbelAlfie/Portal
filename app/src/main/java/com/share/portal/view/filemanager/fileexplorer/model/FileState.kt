package com.share.portal.view.filemanager.fileexplorer.model

sealed interface FileState {
  object Selection: FileState

  object Exploration: FileState
}