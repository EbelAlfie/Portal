package com.share.portal.presentation.filemanager.fileexplorer

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.share.portal.domain.FileUseCaseImpl
import com.share.portal.domain.models.FileParam
import com.share.portal.domain.models.FileTreeEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FileViewModel @Inject constructor(
  private val fileUseCase: FileUseCaseImpl
): ViewModel() {
  private var rootPath: String = FileParam.EXTERNAL.pathName

  private val _fileData = MutableStateFlow<FileTreeEntity?>(null)
  val fileData: StateFlow<FileTreeEntity?> = _fileData //Stack of parent file

  private val _errorFile = MutableStateFlow<Exception?>(null)
  val errorFile: StateFlow<Exception?> = _errorFile

  private val fileTraverseStack = MutableStateFlow<List<FileTreeEntity>>(listOf())

  val canGoBack by derivedStateOf { fileTraverseStack.value.size > 1 }

  init {
    getAllFiles()
  }

  fun setRootPath(newRoot: String) {
    rootPath = newRoot.ifBlank { FileParam.EXTERNAL.pathName }
  }

  fun getAllFiles() {
    viewModelScope.launch {
      try {
        val data = fileUseCase.getAllExternalFiles(rootPath)
        _fileData.value = data
      } catch (error: Exception) {
        _errorFile.value = error
      }
    }
  }

  fun goBack() { //pop
  }
}