package com.share.portal.data.repository

import com.share.portal.domain.models.FileTreeEntity

interface FileRepository {
  fun getAllExternalFiles(rootPath: String = ""): FileTreeEntity
}