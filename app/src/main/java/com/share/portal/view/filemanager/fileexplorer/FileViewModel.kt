package com.share.portal.view.filemanager.fileexplorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.share.portal.domain.FileUseCaseImpl
import com.share.portal.domain.models.FileParam
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class FileViewModel @Inject constructor(
  private val fileUseCase: FileUseCaseImpl
): ViewModel() {

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
          if (oldState is FileUiState.FileExplore) {
            oldState.allFiles.add(data)
            oldState
          } else {
            FileUiState.FileExplore(
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
    return (_fileUiState.value as? FileUiState.FileExplore)?.let {
      it.allFiles.size > 0
    } ?: false
  }

  fun goBack() {
    _fileUiState.update { oldState ->
      if (oldState is FileUiState.FileExplore) {
        oldState.allFiles.removeLastOrNull()
      }
      if (oldState is FileUiState.FileSelect) {
        FileUiState.FileExplore(
          oldState.selectedFile
        )
      }
      oldState
    }
  }

  fun onFileClicked(filePath: String) {
    if (_fileUiState.value is FileUiState.FileExplore)
      getAllChildrenFiles(rootFile = filePath)
    if (_fileUiState.value is FileUiState.FileSelect)
      _fileUiState.update { state ->
        (state as? FileUiState.FileExplore)?.let  {
          FileUiState.FileSelect(
            selectedFile = it.allFiles
          )
        }
        state
      }
  }

  fun onLongHold(file: File) {
    _fileUiState.update { oldState ->
      (oldState as? FileUiState.FileSelect)?.let {
        val addedFile = it.selectedFile
        addedFile.add(file)
        it.copy(
          selectedFile = addedFile
        )
      }
      oldState
    }
  }

}