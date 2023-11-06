package com.share.portal.data.datasource

import com.share.portal.domain.models.FileEntity

interface OfflineDataSource {
  fun getAllExternalFiles(): List<FileEntity>
}