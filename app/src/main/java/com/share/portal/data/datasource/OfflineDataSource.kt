package com.share.portal.data.datasource

import com.share.portal.data.models.FileModel

interface OfflineDataSource {
  fun getAllExternalFiles(): List<FileModel>
}