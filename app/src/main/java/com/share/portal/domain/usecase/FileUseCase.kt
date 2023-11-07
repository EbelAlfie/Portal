package com.share.portal.domain.usecase

import java.io.File

interface FileUseCase {
  fun getAllExternalFiles(rootPath: String = ""): List<File>
}