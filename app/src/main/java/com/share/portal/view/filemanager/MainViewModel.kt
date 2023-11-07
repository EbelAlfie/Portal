package com.share.portal.view.filemanager

import androidx.lifecycle.ViewModel
import com.share.portal.domain.FileUseCaseImpl
import com.share.portal.domain.models.FileTreeEntity
import java.io.File
import javax.inject.Inject

class MainViewModel @Inject constructor(
  private val fileUseCase: FileUseCaseImpl
): ViewModel() {

  private var rootPath: String = ""

  fun setRootPath(newRoot: String) {
    rootPath = newRoot
  }

  fun getAllFiles(): FileTreeEntity =
    fileUseCase.getAllExternalFiles(rootPath)
}