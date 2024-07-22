package com.share.portal.presentation.filemanager.fileexplorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.share.portal.domain.models.FileParam
import com.share.portal.domain.usecase.FileUseCase
import com.share.portal.presentation.filemanager.fileexplorer.FileUiState.FileScreen
import com.share.portal.presentation.utils.getChildFiles
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
              previewMode = PreviewMode.Explore(allFiles.getChildFiles())
            )
          } else {
            FileUiState.FileScreen(
              allFiles = mutableListOf(data),
              previewMode = PreviewMode.Explore(mutableListOf(data).getChildFiles())
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
            allFiles = popedFile,
            previewMode = PreviewMode.Explore(popedFile.getChildFiles()) //Always back to explore state first
          )
        }

        else -> oldState
      }
    }
  }

  private fun selectFile(filePosition: Int) {
    _fileUiState.update { oldState ->
      (oldState as FileUiState.FileScreen).let {
        with (it.previewMode as PreviewMode.Select) {
          files[filePosition].isSelected = !files[filePosition].isSelected
          it.copy() //TODO
        }
      }
    }
  }

  fun onFileClicked(filePath: String, filePosition: Int) {
    with (_fileUiState.value as FileUiState.FileScreen) {
      when (previewMode) {
        is PreviewMode.Explore -> getAllChildrenFiles(rootFile = filePath)
        is PreviewMode.Select -> selectFile(filePosition) //TODO
      }
    }

  }

  fun onFileHold(filePosition: Int) {
    with (_fileUiState.value as FileUiState.FileScreen) {
      when (previewMode) {
        is PreviewMode.Explore -> switchOperationMode()
        is PreviewMode.Select -> selectFile(filePosition)
      }
    }
  }

  private fun switchOperationMode() {
    _fileUiState.update { oldState ->
      (oldState as? FileUiState.FileScreen)?.let {
        it.copy(
          previewMode = when (it.previewMode) {
            is PreviewMode.Explore -> PreviewMode.Select(it.allFiles.getChildFiles())
            is PreviewMode.Select -> PreviewMode.Explore(it.allFiles.getChildFiles())
          }
        )
      } ?: oldState
    }
  }

}