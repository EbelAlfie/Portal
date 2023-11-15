package com.share.portal.data.datasource

import java.net.InetSocketAddress

interface OnlineDataSource {

  //fun establishConnectionAsClient(): ResponseModel<FileTreeEntity>

  fun establishWSServer()
  fun requestConnection(address: InetSocketAddress)
}