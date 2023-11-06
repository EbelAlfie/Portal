package com.share.portal.domain.usecase

import com.share.portal.domain.models.FileEntity

interface FileUseCase {
  fun getAllExternalFiles(): List<FileEntity>
}