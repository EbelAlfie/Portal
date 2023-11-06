package com.share.portal.data.repository

import java.io.File

interface FileRepository {
  fun getAllExternalFiles(): List<File>
}