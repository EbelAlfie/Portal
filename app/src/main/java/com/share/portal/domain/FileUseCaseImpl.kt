package com.share.portal.domain

import com.share.portal.data.repository.FileRepository
import com.share.portal.domain.models.FileEntity
import com.share.portal.domain.usecase.FileUseCase
import javax.inject.Inject

class FileUseCaseImpl @Inject constructor(private val fileRepository: FileRepository): FileUseCase {
  override fun getAllExternalFiles(): List<FileEntity>  {
    return fileRepository.getAllExternalFiles()
  }
}