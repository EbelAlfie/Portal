package com.share.portal.domain

import com.share.portal.data.FileRepositoryImpl
import com.share.portal.domain.models.FileEntity
import com.share.portal.domain.usecase.FileUseCase
import javax.inject.Inject

class FileUseCaseImpl @Inject constructor(private val fileRepository: FileRepositoryImpl): FileUseCase {
  override fun getAllExternalFiles(): List<FileEntity>  {
    return fileRepository.getAllExternalFiles()
  }
}