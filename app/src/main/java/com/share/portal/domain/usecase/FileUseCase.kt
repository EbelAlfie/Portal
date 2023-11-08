package com.share.portal.domain.usecase

import com.share.portal.data.models.ResponseModel
import com.share.portal.domain.models.FileTreeEntity

interface FileUseCase {
  fun getAllExternalFiles(rootPath: String = ""): ResponseModel<FileTreeEntity>
}