package com.share.portal.data.datasource

import com.share.portal.domain.models.FileTreeEntity

interface OfflineDataSource {
  fun getAllExternalFiles(rootPath: String = ""): FileTreeEntity
}