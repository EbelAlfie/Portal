package com.share.portal.view.filemanager.fileexplorer

import androidx.lifecycle.ViewModel
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
  private var rootPath: String

  private val _fileData = MutableStateFlow<FileTreeEntity?>(null)
  val fileData: StateFlow<FileTreeEntity?> = _fileData

  private val _errorFile = MutableStateFlow<Exception?>(null)
  fun errorFile(): StateFlow<Exception?> = _errorFile

  init {
    rootPath = FileParam.EXTERNAL.pathName
    getAllFiles()
  }

  fun setRootPath(newRoot: String) {
    rootPath = newRoot.ifBlank { FileParam.EXTERNAL.pathName }
  }

  fun getAllFiles() {
    CoroutineScope(Dispatchers.IO).launch {//TODO VIEWMODEL SCOPE
      try {
        val data = fileUseCase.getAllExternalFiles(rootPath)
        _fileData.value = data
      } catch (error: Exception) {
        _errorFile.value = error
      }
    }
  }
}