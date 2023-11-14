package com.share.portal.data.repository

import com.share.portal.domain.models.FileTreeEntity
import java.io.File

interface FileRepository {
  fun getAllExternalFiles(rootPath: String = ""): FileTreeEntity

  fun sendFile(file: File)

  fun receiveFile(): FileTreeEntity
}