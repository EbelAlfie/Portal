package com.share.portal.data.datasource

import java.io.File

interface OfflineDataSource {
  fun getAllExternalFiles(rootPath: String = ""): List<File>
}