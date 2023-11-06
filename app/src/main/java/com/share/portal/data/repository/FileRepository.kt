package com.share.portal.data.repository

import com.share.portal.domain.models.FileEntity

interface FileRepository {
  fun getAllExternalFiles(): List<FileEntity>
}