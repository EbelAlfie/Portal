package com.share.portal.domain

import com.share.portal.data.FileRepositoryImpl
import com.share.portal.domain.usecase.FileUseCase
import java.io.File
import javax.inject.Inject

class FileUseCaseImpl @Inject constructor(private val fileRepository: FileRepositoryImpl): FileUseCase {
  override fun getAllExternalFiles(): List<File>  {
    return fileRepository.getAllExternalFiles()
  }
}