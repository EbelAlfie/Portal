package com.share.portal.main

import androidx.lifecycle.ViewModel
import com.share.portal.domain.FileUseCaseImpl
import com.share.portal.domain.models.FileEntity
import javax.inject.Inject

class MainViewModel @Inject constructor(
  private val fileUseCase: FileUseCaseImpl
): ViewModel() {
  fun getGreet() : String = "Hey"

  fun getAllFiles() : List<FileEntity> =
    fileUseCase.getAllExternalFiles()
}