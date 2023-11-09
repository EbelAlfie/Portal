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
    rootPath = FileParam.EXTERNAL.pathName
  }

  fun setRootPath(newRoot: String) {
    rootPath = newRoot.ifBlank { FileParam.EXTERNAL.pathName }
  }

  fun getAllFiles(
    onSuccess: (FileTreeEntity) -> Unit,
    onFailed: (Throwable) -> Unit
  ) {
    try {
      val data = fileUseCase.getAllExternalFiles(rootPath)
      onSuccess.invoke(data)
    } catch (error: Exception) {
      onFailed.invoke(error)
    }
  }
}