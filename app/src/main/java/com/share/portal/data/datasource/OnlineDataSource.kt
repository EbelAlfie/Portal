package com.share.portal.data.datasource

import com.share.portal.data.models.ResponseModel
import com.share.portal.domain.models.FileTreeEntity

interface OnlineDataSource {

  fun establishConnectionAsClient(): ResponseModel<FileTreeEntity>

  fun establishConnectionAsServer(packet: FileTreeEntity)
}