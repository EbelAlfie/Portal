package com.share.portal.view.filemanager.fileexplorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.share.portal.domain.FileUseCaseImpl
import com.share.portal.domain.models.FileParam
import com.share.portal.view.filemanager.fileexplorer.OperationMode.FileSelect
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class FileViewModel @Inject constructor(
  private val fileUseCase: FileUseCaseImpl
) : ViewModel() {

  private val _fileUiState = MutableStateFlow<FileUiState>(FileUiState.Loading)
  val fileUiState: StateFlow<FileUiState> = _fileUiState.asStateFlow()

  init {
    getAllChildrenFiles()
  }

  fun getAllChildrenFiles(rootFile: String = FileParam.EXTERNAL.pathName) {
    val rootPath = rootFile.ifBlank { FileParam.EXTERNAL.pathName }
    viewModelScope.launch {
      try {
        val data = fileUseCase.getAllExternalFiles(rootPath)
        _fileUiState.update { oldState ->
          if (oldState is FileUiState.Loaded) {
            oldState.copy(
              allFiles = oldState.allFiles + data
            )
          } else {
            FileUiState.Loaded(
              allFiles = mutableListOf(data)
            )
          }
        }
      } catch (error: Exception) {
        _fileUiState.value = FileUiState.Error(
          cause = error
        )
      }
    }
  }

  fun canGoBack(): Boolean {
    val uiState = _fileUiState.value
    return when (uiState) {
      is FileUiState.Loaded ->
        uiState.allFiles.size > 1 || uiState.operationMode is OperationMode.FileSelect
      else -> false
    }
  }

  fun goBack() {
    _fileUiState.update { oldState ->
      when (oldState) {
        is FileUiState.Loaded -> {
          val popedFile = oldState.allFiles.toMutableList()
          if (oldState.operationMode is FileSelect) {
            oldState.copy(
              allFiles = popedFile,
              operationMode = OperationMode.FileExplore
            )
          } else {
            popedFile.removeLastOrNull()
            oldState.copy(
              allFiles = popedFile
            )
          }
        }
        else -> oldState
      }
    }
  }

  fun onFileClicked(filePosition: Int) {
    (_fileUiState.value as? FileUiState.Loaded)?.let {
      getAllChildrenFiles(rootFile = it.allFiles.last().child[filePosition].path)
    }
  }

  fun switchOperationMode(filePosition: Int) {
    _fileUiState.update { oldState ->
      if (oldState is FileUiState.Loaded) {
        FileUiState.Loaded(
          allFiles = oldState.allFiles.toMutableList(),
          operationMode = OperationMode.FileSelect()
        )
      } else
        oldState
    }
  }

  fun selectFile(filePosition: Int) {
    _fileUiState.update { oldState ->
      (oldState as FileUiState.Loaded).let {
        val selectedFile = (it.operationMode as FileSelect).selectedFiles + filePosition
        it.copy(
          operationMode = OperationMode.FileSelect(
            selectedFiles = selectedFile
          )
        )
      }
    }
  }

}