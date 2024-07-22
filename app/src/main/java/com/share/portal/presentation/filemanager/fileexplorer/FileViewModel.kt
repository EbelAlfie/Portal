package com.share.portal.presentation.filemanager.fileexplorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.share.portal.domain.models.FileParam
import com.share.portal.domain.usecase.FileUseCase
import com.share.portal.presentation.filemanager.fileexplorer.FileUiState.FileScreen
import com.share.portal.presentation.filemanager.fileexplorer.model.FileData
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
            val allFiles = oldState.allFiles + data
            oldState.copy(
              allFiles = allFiles,
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
        is FileUiState.FileScreen -> handleFileScreenBack(oldState)
        else -> oldState
      }
    }
  }

  private fun handleFileScreenBack(oldState: FileScreen): FileScreen {
    return when (oldState.previewMode) {
      is PreviewMode.Explore -> {
        val popedFile = oldState.allFiles.toMutableList()
        popedFile.removeLastOrNull()
        oldState.copy(allFiles = popedFile)
      }
      is PreviewMode.Select -> {
        oldState.copy(previewMode = PreviewMode.Explore)
      }
    }
  }

  private fun selectFile(fileData: FileData) {
    _fileUiState.update { currentState ->
      with (currentState as FileUiState.FileScreen) {
        val newCachedFile = when (previewMode) {
          is PreviewMode.Explore -> listOf(fileData)
          is PreviewMode.Select -> {
            with(previewMode) {
              if (selectedFiles.contains(fileData))
                selectedFiles - fileData
              else
                selectedFiles + fileData
            }
          }
        }
        copy(previewMode = PreviewMode.Select(newCachedFile.toMutableList()))
      }
    }
  }

  fun onFileClicked(filePath: String, fileData: FileData) {
    with (_fileUiState.value as FileUiState.FileScreen) {
      when (previewMode) {
        is PreviewMode.Explore -> getAllChildrenFiles(rootFile = filePath)
        is PreviewMode.Select -> selectFile(fileData)
      }
    }
  }

  fun onFileHold(fileData: FileData) {
    selectFile(fileData)
  }

}