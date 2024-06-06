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
import javax.inject.Inject

class FileViewModel @Inject constructor(
  private val fileUseCase: FileUseCaseImpl
): ViewModel() {

  private val _fileUiState = MutableStateFlow<FileUiState>(FileUiState.Loading)
  val fileUiState: StateFlow<FileUiState> = _fileUiState.asStateFlow()

  init {
    getAllFiles()
  }

  fun getAllFiles(rootFile: String = FileParam.EXTERNAL.pathName) {
    val rootPath = rootFile.ifBlank { FileParam.EXTERNAL.pathName }
    viewModelScope.launch {
      try {
        val data = fileUseCase.getAllExternalFiles(rootPath)
        _fileUiState.update { oldState ->
          if (oldState is FileUiState.Loaded) {
            oldState.files.add(data)
            oldState
          } else {
            FileUiState.Loaded(
              files = mutableListOf(data)
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
    return (_fileUiState.value as? FileUiState.Loaded)?.let {
      it.files.size > 0
    } ?: false
  }

  fun goBack() {
    _fileUiState.update { oldState ->
      (oldState as FileUiState.Loaded).also {
        it.files.removeLastOrNull()
      }
    }
  }
}