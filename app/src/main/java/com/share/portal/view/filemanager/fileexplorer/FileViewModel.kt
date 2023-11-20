package com.share.portal.view.filemanager.fileexplorer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.share.portal.domain.FileUseCaseImpl
import com.share.portal.domain.models.FileParam
import com.share.portal.domain.models.FileTreeEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FileViewModel @Inject constructor(
  private val fileUseCase: FileUseCaseImpl
): ViewModel() {
  private var rootPath: String

  private val selectedFile: MutableList<String> = mutableListOf()

  private val _fileData = MutableLiveData<FileTreeEntity>()
  fun fileData(): LiveData<FileTreeEntity> = _fileData

  private val _errorFile = MutableLiveData<Exception>()
  fun errorFile(): LiveData<Exception> = _errorFile

  init {
    rootPath = FileParam.EXTERNAL.pathName
    getAllFiles()
  }

  fun setRootPath(newRoot: String) {
    rootPath = newRoot.ifBlank { FileParam.EXTERNAL.pathName }
  }

  fun getAllFiles() {
    CoroutineScope(Dispatchers.IO).launch {
      try {
        val data = fileUseCase.getAllExternalFiles(rootPath)
        _fileData.postValue(data)
      } catch (error: Exception) {
        _errorFile.postValue(error)
      }
    }
  }

  fun storeFile(filePath: String) {
    selectedFile.add(filePath)
  }

  fun destoreFile() {
    selectedFile.clear()
  }
}