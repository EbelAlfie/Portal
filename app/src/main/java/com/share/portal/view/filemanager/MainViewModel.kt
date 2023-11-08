package com.share.portal.view.filemanager

import androidx.lifecycle.ViewModel
import com.share.portal.domain.FileUseCaseImpl
import com.share.portal.domain.models.FileParam
import com.share.portal.domain.models.FileTreeEntity
import javax.inject.Inject

class MainViewModel @Inject constructor(
  private val fileUseCase: FileUseCaseImpl
): ViewModel() {
  private var rootPath: String

  init {
    rootPath = FileParam.EXTERNAL.rootName
  }

  fun setRootPath(newRoot: String) {
    rootPath = newRoot
  }

  fun getAllFiles(onSuccess: (FileTreeEntity) -> Unit, onFailed: (Throwable) -> Unit) {
    val data = fileUseCase.getAllExternalFiles(rootPath)
    if (data.data != null) onSuccess.invoke(data.data)
    if (data.error != null) onFailed.invoke(data.error)
  }
}