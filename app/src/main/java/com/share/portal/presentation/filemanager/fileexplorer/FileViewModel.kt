package com.share.portal.presentation.filemanager.fileexplorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.share.portal.domain.models.FileParam
import com.share.portal.domain.usecase.FileUseCase
import com.share.portal.presentation.filemanager.fileexplorer.FileUiState.FileScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FileViewModel @Inject constructor(
  private val fileUseCase: FileUseCase
) : ViewModel() {

  private val _fileUiState = MutableStateFlow<FileUiState>(FileUiState.Loading)
  val fileUiState: StateFlow<FileUiState> = _fileUiState.asStateFlow()

  init {
    getAllChildrenFiles()
  }

  private fun getAllChildrenFiles(rootFile: String = FileParam.EXTERNAL.pathName) {
    val rootPath = rootFile.ifBlank { FileParam.EXTERNAL.pathName }
    viewModelScope.launch {
      try {
        val data = fileUseCase.getAllExternalFiles(rootPath)
        _fileUiState.update { oldState ->
          if (oldState is FileUiState.FileScreen) {
            oldState.copy(
              allFiles = oldState.allFiles + data,
              previewMode = PreviewMode.Explore
            )
          } else {
            FileUiState.FileScreen(
              allFiles = mutableListOf(data),
              previewMode = PreviewMode.Explore
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
    return (_fileUiState.value as? FileUiState.FileScreen)?.let {
      return when (it.previewMode) {
        is PreviewMode.Explore -> it.allFiles.size > 1
        is PreviewMode.Select -> true
      }
    } ?: false
  }

  fun goBack() {
    _fileUiState.update { oldState ->
      when (oldState) {
        is FileUiState.FileScreen -> {
          val popedFile = oldState.allFiles.toMutableList()
          popedFile.removeLastOrNull()
          oldState.copy(
            allFiles = popedFile
          )
        }

        else -> oldState
      }
    }
  }

  fun onFileClicked(filePath: String) {
    if (_fileUiState.value is FileUiState.FileScreen)
      getAllChildrenFiles(rootFile = filePath)
  }

  fun onFileHold(filePosition: Int) {
    switchOperationMode(filePosition)
  }

  fun switchOperationMode(filePosition: Int) {
    _fileUiState.update { oldState ->
      if (oldState is FileUiState.FileScreen) {
        FileUiState.FileSelect(
          allFiles = oldState.allFiles.toMutableList(),
          selectedIndices = mutableListOf(filePosition)
        )
      } else
        oldState
    }
  }

  fun selectFile(filePosition: Int) {
    _fileUiState.update { oldState ->
      (oldState as FileUiState.FileSelect).let {
        val selectedIndex = it.selectedIndices + filePosition
        it.copy(
          selectedIndices = selectedIndex.toMutableList()
        )
      }
    }
  }

}