package com.share.portal.data.datasource

import com.share.portal.data.models.ResponseModel
import com.share.portal.domain.models.FileTreeEntity

interface OfflineDataSource {
  suspend fun getAllExternalFiles(rootPath: String = ""): ResponseModel<FileTreeEntity>
}