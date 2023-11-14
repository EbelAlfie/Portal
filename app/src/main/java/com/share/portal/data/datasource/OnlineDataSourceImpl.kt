package com.share.portal.data.datasource

import com.share.portal.data.models.ResponseModel
import com.share.portal.domain.models.FileTreeEntity
import javax.inject.Inject

class OnlineDataSourceImpl @Inject constructor(): OnlineDataSource {
  override fun establishConnectionAsClient(): ResponseModel<FileTreeEntity> {

  }

  override fun establishConnectionAsServer(packet: FileTreeEntity) {

  }
}